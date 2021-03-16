package com.test.apply.thread;

public class PrintInTern {
    public static void main(String[] args) {
        m();
    }

    private static int i = 1;
    private static char c = 'A';

    private static void m() {
        Thread numT = new Thread(() -> {
            synchronized (PrintInTern.class) {
                while (i <= 26) {
                    System.out.println("numT: " + i++);
                    try {
                        PrintInTern.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PrintInTern.class.notifyAll();
                }
            }
        }, "num");

        Thread letterT = new Thread(() -> {
            synchronized (PrintInTern.class) {
                while (c <= 'Z') {
                    System.out.println("letterT: " + c++);
                    PrintInTern.class.notifyAll();
                    try {
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
