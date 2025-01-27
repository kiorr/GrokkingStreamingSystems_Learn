package com.streamwork.ch02.api;

import com.streamwork.ch02.func.ApplyFunc;

import java.util.List;

public class DistributedOperator extends Operator {
    public ApplyFunc applyFunc;

    public DistributedOperator(String name, ApplyFunc func) {
        super(name);
        this.applyFunc = func;
    }

    public void apply(Event event, List<Event> eventCollector) {
        System.out.println("Distributed operator " + getName() + " called");
        applyFunc.apply(event, eventCollector);
        eventCollector.add(event);
    }

}
