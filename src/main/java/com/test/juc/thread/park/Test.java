package com.test.juc.thread.park;

import com.jmc.lang.Threads;
import com.test.main.Tools;

import java.util.concurrent.locks.LockSupport;

public class Test {
    public static void main(String[] args) {
        // 线程暂停
        var t = new Thread(() -> {
            System.err.println("t: park");
            LockSupport.park();
            System.err.println("t: unpark");
        });

        t.start();

        Threads.sleep(3000);
        LockSupport.unpark(t);
    }
}
