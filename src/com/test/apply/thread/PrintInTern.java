package com.test.apply.thread;

import java.util.concurrent.TimeUnit;

public class PrintInTern {
    public static void main(String[] args) {
        solve();
    }

    private static int i = 1;
    private static char c = 'A';

    private static void solve() {
        Thread numT = new Thread(() -> {
            synchronized (PrintInTern.class) {
                while (i <= 26) {
                    System.out.println("numT: " + i++);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        PrintInTern.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PrintInTern.class.notify();
                }
            }
        }, "num");

        Thread letterT = new Thread(() -> {
            synchronized (PrintInTern.class) {
                while (c <= 'Z') {
                    System.out.println("letterT: " + c++);
                    PrintInTern.class.notify();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        PrintInTern.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "letter");

        numT.start();
        letterT.start();
    }
}
