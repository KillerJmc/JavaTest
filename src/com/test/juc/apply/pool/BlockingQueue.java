package com.test.juc.apply.pool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者阻塞队列
 * @param <T> 元素类型
 */
public class BlockingQueue<T> {
    /**
     * 内部的队列
     */
    private final Queue<T> queue = new ArrayDeque<>();

    /**
     * 阻塞队列的容量
     */
    private final int capacity;

    /**
     * 锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 队列已空条件
     */
    private final Condition emptyWaitingCondition = lock.newCondition();

    /**
     * 队列已满条件
     */
    private final Condition fullWaitingCondition = lock.newCondition();

    /**
     * 获取阻塞队列
     * @param capacity 容量
     */
    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 添加元素到阻塞队列中
     * @param t 元素
     * @param waitTime 等待时间
     * @param unit 时间单位
     * @return 如果等待时间到返回false（获取元素失败），否则返回true
     */
    public boolean add(T t, long waitTime, TimeUnit unit) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                if (!fullWaitingCondition.await(waitTime, unit)) {
                    return false;
                }
            }
            queue.add(t);
            // 放入一个元素只需要消费者唤醒一次，效率高
            emptyWaitingCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }

    /**
     * 从阻塞队列中获取元素
     * @param waitTime 等待时间
     * @param unit 时间单位
     * @return 如果等待时间到返回null，否则返回该元素
     */
    public T poll(long waitTime, TimeUnit unit) {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                if (!emptyWaitingCondition.await(waitTime, unit)) {
                    return null;
                }
            }
            var res = queue.poll();
            // 获取一个元素只需要唤醒生产者一次，效率高
            fullWaitingCondition.signal();
            return res;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 返回阻塞队列中元素个数
     * @return 队列中元素个数
     */
    public synchronized int size() {
        return queue.size();
    }
}
