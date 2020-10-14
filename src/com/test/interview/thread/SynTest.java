package com.test.interview.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SynTest {
    private static int c = 0;
    @Test
    public void NonSyn() throws InterruptedException {
        Thread[] ts = new Thread[100];
        var latch = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                for (int k = 0; k < 10000; k++) c++;
                latch.countDown();
            });
        }

        for (Thread t : ts) t.start();
        latch.await();
        System.out.println(c);
    }

    private static int c2 = 0;
    @Test
    public void withSyn() throws InterruptedException {
        Object o = new Object();
        Thread[] ts = new Thread[100];
        var latch = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                synchronized(o) {
                    for (int k = 0; k < 10000; k++) c2++;
                }
                latch.countDown();
            });
        }

        for (Thread t : ts) t.start();
        latch.await();
        System.out.println(c2);
    }

    @Test
    public void withCasAtomic() throws InterruptedException {
        AtomicInteger c = new AtomicInteger();
        Thread[] ts = new Thread[100];
        var latch = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                for (int k = 0; k < 10000; k++) c.getAndIncrement();
                latch.countDown();
            });
        }

        for (Thread t : ts) t.start();
        latch.await();
        System.out.println(c.get());
    }
}
