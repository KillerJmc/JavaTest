package com.jmc.lang;

import static com.jmc.lang.Tries.*;

public class Timers {
    public static void secondTimer(RunnableThrowsException r, String name) {
        long startTime = System.currentTimeMillis();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        long endTime = System.currentTimeMillis();
        System.out.println(name + "耗时：" + (endTime - startTime) / 1000 + "秒");
    }
    public static void secondTimer(RunnableThrowsException r) {
        secondTimer(r, "");
    }

    public static void milliTimer(RunnableThrowsException r, String name) {
        long startTime = System.currentTimeMillis();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        long endTime = System.currentTimeMillis();
        System.out.println(name + "耗时：" + (endTime - startTime) + "毫秒");
    }
    public static void milliTimer(RunnableThrowsException r) {
        milliTimer(r, "");
    }

    public static void nanoTimer(RunnableThrowsException r, String name) {
        long startTime = System.nanoTime();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        long endTime = System.nanoTime();
        System.out.println(name + "耗时：" + (endTime - startTime) + "纳秒");
    }
    public static void nanoTimer(RunnableThrowsException r) {
        nanoTimer(r, "");
    }

}
