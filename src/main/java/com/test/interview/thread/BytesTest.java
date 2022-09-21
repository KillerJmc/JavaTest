package com.test.interview.thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class BytesTest {
    public static final long COUNT = 100000000L;
    @Test
    public void bytesTest01() throws InterruptedException {
        var latch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            T1 t = new T1();
            for (int i = 0; i < COUNT; i++) t.x = i;
            latch.countDown();
        });

        Thread t2 = new Thread(() -> {
            T1 t = new T1();
            for (int i = 0; i < COUNT; i++) t.x = i;
            latch.countDown();
        });

        t1.start();
        t2.start();
        latch.await();
    }
    private static class T1 {
        public volatile long x = 0L;
    }

    @Test
    public void bytesTest02() throws InterruptedException {
        var latch = new CountDownLatch(2);
        Thread t1 = new Thread(() -> {
            T2 t = new T2();
            for (int i = 0; i < COUNT; i++) t.x = i;
            latch.countDown();
        });

        Thread t2 = new Thread(() -> {
            T2 t = new T2();
            for (int i = 0; i < COUNT; i++) t.x = i;
            latch.countDown();
        });

        t1.start();
        t2.start();
        latch.await();
    }
    private static class T2 {
        private long l1, l2, l3, l4, l5, l6, l7;
        public volatile long x = 0L;
        private long l8, l9, l10, l11, l12, l13, l14;
    }
}
