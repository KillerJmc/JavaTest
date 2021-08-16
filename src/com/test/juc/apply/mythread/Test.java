package com.test.juc.apply.mythread;

import com.jmc.lang.extend.Tries;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        var t = new MyThread<>(() -> {
            System.err.println("t: start");

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                System.err.println("t: sleep interrupted.");
                return null;
            }

            return "res here";
        });

        t.start();

        Tries.tryThis(() -> TimeUnit.SECONDS.sleep(1));
        System.err.println("Main: attempt to stop the thread.");
        Tries.tryThis(() -> TimeUnit.SECONDS.sleep(1));
        t.stop();

        Tries.tryThis(() -> TimeUnit.SECONDS.sleep(2));
        System.err.println("Main: attempt to get the result.");
        System.out.println(t.get());
    }
}
