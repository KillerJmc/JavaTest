package com.test.interview.thread;

import com.jmc.lang.Threads;
import com.test.main.Tools;

/**
 * volatile：
 * 1.保持变量在线程间的可见性
 * 2.禁止指令重排序（如对对象的初始化）
 *
 */
public class VolatileTest {
    public static void main(String[] args) {
        test();
    }

    private static /*volatile*/ boolean flag = true;
    public static void test() {
        new Thread(() -> {
            System.out.println("start...");
            while (flag) {}
            System.out.println("end...");
        }).start();
        Threads.sleep(1000);
        flag = false;
    }
}
