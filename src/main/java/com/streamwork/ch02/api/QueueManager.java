package com.streamwork.ch02.api;

import com.streamwork.ch02.engine.EventQueue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * 作者：fu
 * 日期：2025/1/26 10:35
 */
public class QueueManager {
    private static final Map<String, EventQueue> queueMap = new HashMap<>();

    public static EventQueue getQueue(List<String> addresses) {
        if (addresses == null || addresses.isEmpty()) {
            throw new IllegalArgumentException("Queue addresses cannot be null or empty");
        }
        return queueMap.computeIfAbsent(addresses.get(0), key -> new EventQueue(64)); // 默认单地址
    }

    public static void registerQueue(String address, EventQueue queue) {
        queueMap.put(address, queue);
    }
}
