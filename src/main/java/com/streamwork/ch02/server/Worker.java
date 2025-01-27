package com.streamwork.ch02.server;

import com.streamwork.ch02.api.*;
import com.streamwork.ch02.engine.EventQueue;
import com.streamwork.ch02.engine.OperatorExecutor;
import com.streamwork.ch02.func.ApplyFunc;
import com.streamwork.ch02.job.VehicleEvent;
import com.streamwork.ch02.rpc.io.RpcNode;

import java.util.ArrayList;
import java.util.List;

public class Worker extends RpcNode {
    // 负责新开线程运行指定的任务（带并行度）
    // 线程间共享内存，将接收到的事件放入对应的事件队列
    // 可以读取配置
    public OperatorExecutor executor;

    // This list is used for accepting events from user logic.
    protected final List<Event> eventCollector = new ArrayList<Event>();
    // Data queues for the upstream processes
    protected EventQueue incomingQueue = null;
    // Data queue for the downstream processes
    protected EventQueue outgoingQueue = null;


    public Worker(String operatorType, ApplyFunc func) {
        // 需求一：实现一个算子worker，可接收指定队列的事件
        DistributedOperator op = new DistributedOperator(operatorType, func);
        this.executor = new OperatorExecutor(op);

        // 创建组件间的事件队列
        this.incomingQueue = new EventQueue(60);
        executor.setIncomingQueue(incomingQueue);

        this.outgoingQueue = new EventQueue(60);
        executor.setOutgoingQueue(outgoingQueue);
    }

    public void work() {
        executor.start();
    }

    public void addIncomingQueue() {
        System.out.println("addIncomingQueue");
//        incomingQueue.add(event);
    }

    public Task requestTaskFromMaster() {
        return (Task) call(masterPort, "assignTask", new Object[]{});
    }
    public void executeTask(Task task) {
        // 根据 Task 信息加载算子
        Operator operator = OperatorFactory.create(task.getOperatorType());
        operator.setLogic(task.getLogic());
        operator.setParallelism(task.getParallelism());

        // 配置上下游队列
        EventQueue upstreamQueue = QueueManager.getQueue(task.getUpstreamQueues());
        EventQueue downstreamQueue = QueueManager.getQueue(task.getDownstreamQueues());

        operator.setIncomingQueue(upstreamQueue);
        operator.setOutgoingQueue(downstreamQueue);

        // 启动任务
        operator.start();
    }
    public static void main(String[] args) {
        Worker worker = new Worker();
        Task task = worker.requestTaskFromMaster();
        QueueManager.registerQueue("upstreamQueue", incomingQueue);
        QueueManager.registerQueue("downstreamQueue", outgoingQueue);
        worker.executeTask(task);
    }
}
