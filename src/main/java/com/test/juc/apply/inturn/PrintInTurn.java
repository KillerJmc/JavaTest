package com.test.juc.apply.inturn;

import com.jmc.lang.Tries;
import com.test.main.Tools;

import java.util.concurrent.locks.ReentrantLock;

public class PrintInTurn {
    public static void main(String[] args) {
        method2();
    }

    public static void method1() {
        Thread num = new Thread(() -> {
            int i = 1;
            synchronized (PrintInTurn.class) {
                while (i <= 26) {
                    System.err.println("num: " + i++);
                    Tries.tryThis(PrintInTurn.class::wait);
//                    Tools.sleep(1000);
                    PrintInTurn.class.notifyAll();
                }
            }
        });

        Thread letter = new Thread(() -> {
            char c = 'a';
            synchronized (PrintInTurn.class) {
                while (c <= 'z') {
                    System.err.println("letter: " + c++);
                    PrintInTurn.class.notifyAll();
                    Tries.tryThis(PrintInTurn.class::wait);
//                    Tools.sleep(1000);
                }
            }
        });

        num.start();
        Tools.sleep(100);
        letter.start();
    }

    public static void method2() {
        var lock = new ReentrantLock();
        var printNum = lock.newCondition();
        var printLetter = lock.newCondition();

        Thread num = new Thread(() -> {
            int i = 1;
            lock.lock();
            try {
                while (i <= 26) {
                    System.err.println("num: " + i++);
                    Tries.tryThis(printNum::await);
//                    Tools.sleep(1000);
                    printLetter.signal();
                }
            } finally {
                lock.unlock();
            }
        });

        Thread letter = new Thread(() -> {
            char c = 'a';
            lock.lock();
            try {
                while (c <= 'z') {
                    System.err.println("letter: " + c++);
                    printNum.signal();
                    Tries.tryThis(printLetter::await);
//                    Tools.sleep(1000);
                }
            } finally {
                lock.unlock();
            }
        });

        num.start();
        Tools.sleep(100);
        letter.start();
    }
}
