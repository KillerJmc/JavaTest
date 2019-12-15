package com.test.mysql.orm.sorm.utils;

import com.test.mysql.orm.sorm.utils.TryUtils.RunnableThrowsException;

/**
 *  Provide timer methods.
 *  Copied from com.test.Main.Tools
 *  @author Jmc
 */
public class TimerUtils {
    /**
     * a timer records by seconds.
     * @param r Runnable Throws Exception Interface
     */
    public static void timer(RunnableThrowsException r) {
        long startTime = System.currentTimeMillis();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) / 1000 + "秒");
    }

    /**
     * a timer records by millis.
     * @param r Runnable Throws Exception Interface
     */
    public static void milliTimer(RunnableThrowsException r) {
        long startTime = System.currentTimeMillis();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) + "毫秒");
    }

    /**
     * a timer records by nanos.
     * @param r Runnable Throws Exception Interface
     */
    public static void nanoTimer(RunnableThrowsException r) {
        long startTime = System.nanoTime();
        try {r.run();} catch (Exception e) {e.printStackTrace();}
        long endTime = System.nanoTime();
        System.out.println("耗时：" + (endTime - startTime) + "纳秒");
    }
}
