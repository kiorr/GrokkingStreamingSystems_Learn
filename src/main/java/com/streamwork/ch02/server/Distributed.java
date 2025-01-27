package com.streamwork.ch02.server;

import com.streamwork.ch02.api.Task;
import com.streamwork.ch02.job.TestJob;
import com.streamwork.ch02.job.VehicleEvent;
import com.streamwork.ch02.rpc.io.RpcNode;

import java.util.ArrayList;
import java.util.List;

// 负责解析客户端提交的Job，编排Dag并生成工作任务，启动Master节点和Worker节点
public class Distributed extends RpcNode {

    public List<Task> parseDag(DAG dag) {
        List<Task> tasks = new ArrayList<>();
        for (DAGNode node : dag.getNodes()) {
            Task task = new Task(
                    node.getOperatorType(),
                    node.getLogic(),
                    node.getParallelism(),
                    node.getUpstreamQueues(),
                    node.getDownstreamQueues()
            );
            tasks.add(task);
        }
        return tasks;
    }
    public static void main(String[] args) throws Exception {
        TestJob testJob = new TestJob();

        Master master = new Master();
        List<Task> tasks = parseDag(dag); // DAG 解析为任务列表
        for (Task task : tasks) {
            master.addTask(task);
        }
        master.serve(); // 启动 Master 服务

        final Worker worker = new Worker("map", testJob.applyFunc);
//        worker.work();
        worker.setPort(9992);
        worker.serve();

        worker.incomingQueue.add(new VehicleEvent("test"));

        worker.incomingQueue.add(new VehicleEvent("test1"));

        VehicleEvent event2 = new VehicleEvent("test2");

        worker.call(9992, "addIncomingQueue", new Object[]{});
    }
}


