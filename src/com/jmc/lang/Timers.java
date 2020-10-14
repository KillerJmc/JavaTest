package com.jmc.lang;

public class Timers {
    public static void timer(RunnableThrowsException r) {
        long startTime = System.currentTimeMillis();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) / 1000 + "秒");
    }

    public static void milliTimer(RunnableThrowsException r) {
        long startTime = System.currentTimeMillis();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) + "毫秒");
    }

    public static void nanoTimer(RunnableThrowsException r) {
        long startTime = System.nanoTime();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        long endTime = System.nanoTime();
        System.out.println("耗时：" + (endTime - startTime) + "纳秒");
    }

}
