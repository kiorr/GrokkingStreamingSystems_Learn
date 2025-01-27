package com.streamwork.ch02.api;

import java.util.List;
/**
 * 功能：
 * 作者：fu
 * 日期：2025/1/26 09:17
 */


public class Task {
    private String operatorType;       // 算子类型
    private String logic;             // 算子逻辑
    private int parallelism;          // 并行度
    private List<String> upstreamQueues;   // 上游队列地址
    private List<String> downstreamQueues; // 下游队列地址


    public Task(String operatorType, String logic, int parallelism,
                List<String> upstreamQueues, List<String> downstreamQueues) {
        this.operatorType = operatorType;
        this.logic = logic;
        this.parallelism = parallelism;
        this.upstreamQueues = upstreamQueues;
        this.downstreamQueues = downstreamQueues;
    }
    // Getters and Setters
    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public int getParallelism() {
        return parallelism;
    }

    public void setParallelism(int parallelism) {
        this.parallelism = parallelism;
    }

    public List<String> getUpstreamQueues() {
        return upstreamQueues;
    }

    public void setUpstreamQueues(List<String> upstreamQueues) {
        this.upstreamQueues = upstreamQueues;
    }

    public List<String> getDownstreamQueues() {
        return downstreamQueues;
    }

    public void setDownstreamQueues(List<String> downstreamQueues) {
        this.downstreamQueues = downstreamQueues;
    }
}
