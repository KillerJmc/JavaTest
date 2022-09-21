package com.test.juc.apply.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class ThreadPool {
    /**
     * 最大线程数
     */
    private final int coreSize;

    /**
     * 线程集合
     */
    private final List<Runner> threads;

    /**
     * 任务队列
     */
    private final BlockingQueue<Runnable> taskQueue;

    /**
     * 拒绝策略
     */
    private final Consumer<Runnable> rejectPolicy;

    /**
     * 线程池是否处于停止状态
     */
    private volatile boolean stop;

    /**
     * 执行此类的线程
     */
    private final Thread currentThread;

    /**
     * 获取线程池实例
     * @param coreSize 核心线程个数
     * @param taskQueue 任务队列
     * @param rejectPolicy 拒绝策略
     */
    public ThreadPool(int coreSize, BlockingQueue<Runnable> taskQueue, Consumer<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        // 加锁保证线程安全
        this.threads = Collections.synchronizedList(new ArrayList<>());
        this.taskQueue = taskQueue;
        this.rejectPolicy = rejectPolicy;
        this.currentThread = Thread.currentThread();
    }

    /**
     * 任务类
     */
    private class Runner extends Thread {
        public Runner(Runnable target) {
            super(target);
        }

        @Override
        public void run() {
            super.run();

            // 如果任务队列还有任务 或者 线程池还未关闭就 执行任务 或 等待新任务
            while (taskQueue.size() > 0 || !stop) {
                // 每次尝试时间默认100ms
                var runnable = taskQueue.poll(100, TimeUnit.MILLISECONDS);
                if (runnable != null) {
                    runnable.run();
                }
            }

            // 把自己从线程池中删除
            threads.remove(this);
        }
    }

    /**
     * 执行任务
     * @param target 任务
     */
    public void execute(Runnable target) {
        if (stop) {
            throw new IllegalStateException("Thread Pool has been shutdown, can't accept new tasks!");
        }

        // 如果当前线程数小于核心线程数
        if (threads.size() < coreSize) {
            var runner = new Runner(target);
            threads.add(runner);
            runner.start();
        } else {
            // 放入任务阻塞队列，若队列已满默认等待100ms
            if (!taskQueue.add(target, 100, TimeUnit.MILLISECONDS)) {
                // 队列已满就执行拒绝策略
                rejectPolicy.accept(target);
            }
        }
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        stop = true;

        new Thread(() -> {
            while (!threads.isEmpty());
            LockSupport.unpark(currentThread);
        }).start();
    }

    /**
     * 等待线程池完全关闭
     */
    public void awaitTermination() {
        LockSupport.park();
    }

    /**
     * 等待线程池完全关闭
     * @param waitTime 等待时间
     * @param unit 时间单位
     * @return 是否关闭成功
     */
    public boolean awaitTermination(long waitTime, TimeUnit unit) {
        LockSupport.parkNanos(unit.toNanos(waitTime));
        return threads.isEmpty();
    }
}
