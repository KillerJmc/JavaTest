package com.test.juc.thread.queue;

import com.jmc.lang.Threads;
import com.test.main.Tools;

public class Test {
    public static void main(String[] args) {
        var mq = new MessageQueue<Integer>(2);

        // 生产者
        for (int i = 1; i <= 3; i++) {
            int message = i;
            new Thread(() -> mq.put(message)).start();
        }

        Threads.sleep(1000);

        // 消费者
        for (int i = 0; i < 3; i++) {
            new Thread(mq::get).start();
            Threads.sleep(1000);
        }
    }
}
