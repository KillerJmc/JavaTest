package com.test.juc.apply.mythread;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.LockSupport;

public class MyThread<T> {
    private final Thread currentThread;
    private final Thread operatingThread;
    private T result;

    public MyThread(Callable<T> callable) {
        this.currentThread = Thread.currentThread();
        this.operatingThread = new Thread(() -> {
            try {
                this.result = callable.call();
                LockSupport.unpark(currentThread);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void start() {
        operatingThread.start();
    }

    public void stop() {
        operatingThread.interrupt();
    }

    public T get() {
        if (this.result == null) {
            LockSupport.park();
        }
        return this.result;
    }
}
