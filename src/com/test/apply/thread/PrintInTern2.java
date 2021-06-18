package com.test.apply.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class PrintInTern2 {
    public static void main(String[] args) {
        solve();
    }

    private static int i = 1;
    public static char c = 'A';

    private static void solve() {
        var lock = new ReentrantLock();
        var canPrint = lock.newCondition();

        var numT = new Thread(() -> {
            try {
                lock.lock();
                while (i <= 26) {
                    canPrint.signal();
                    System.out.println(i);
                    i++;
                    TimeUnit.SECONDS.sleep(1);
                    canPrint.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "num");

        var letterT = new Thread(() -> {
            try {
                lock.lock();
                while (c <= 'Z') {
                    canPrint.signal();
                    System.out.println(c);
                    c++;
                    TimeUnit.SECONDS.sleep(1);
                    canPrint.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "letter");

        numT.start();
        letterT.start();
    }
}
