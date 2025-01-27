package com.streamwork.ch02.server;

import com.streamwork.ch02.api.Task;
import com.streamwork.ch02.rpc.io.RpcNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*  负责工作节点间的通信  */
public class Master extends RpcNode {

    public Master() {
        // 初始化Master节点
    }

    // TODO 节点检查和重试
    private final Queue<Task> taskQueue = new LinkedList<>();

    public Task assignTask() {
        synchronized (taskQueue) {
            return taskQueue.poll(); // 从队列中取出任务
        }
    }

    public void addTask(Task task) {
        synchronized (taskQueue) {
            taskQueue.offer(task); // 添加任务到队列
        }
    }
    // 负责读取配置



}
