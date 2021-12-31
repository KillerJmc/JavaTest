package com.test.juc.thread.queue;

import com.jmc.lang.Tries;
import com.test.algorithm.list.linked.impl.LinkedQueue;

public class MessageQueue<T> {
    private final LinkedQueue<T> queue;
    private final int capacity;

    public MessageQueue(int capacity) {
        this.queue = new LinkedQueue<>();
        this.capacity = capacity;
    }

    public synchronized void put(T message) {
        // 避免被其他线程虚假唤醒
        while (queue.size() >= capacity) {
            System.err.println("消息队列已满");
            Tries.tryThis(this::wait);
        }

        queue.add(message);
        System.err.println("生产者放入消息：" + message);
        // 通知消费者消费
        notifyAll();
    }

    public synchronized T get() {
        while (queue.isEmpty()) {
            Tries.tryThis(this::wait);
        }

        var res = queue.poll();
        System.out.println("消费者拿走消息：" + res);
        // 通知生产者生产
        notifyAll();
        return res;
    }
}
