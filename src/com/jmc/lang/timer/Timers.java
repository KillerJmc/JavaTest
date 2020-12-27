package com.jmc.lang.timer;

import static com.jmc.lang.extend.Tries.*;
import static java.lang.String.format;

public class Timers {
    private static int times = 1;

    public static void setTimes(int times) {
        Timers.times = times;
    }

    public static void secondTimer(RunnableThrowsException r, String name) {
        printTimer(name, nano(r), 9);
    }
    public static void secondTimer(RunnableThrowsException r) {
        secondTimer(r, "");
    }

    public static void milliTimer(RunnableThrowsException r, String name) {
        printTimer(name, nano(r), 6);
    }
    public static void milliTimer(RunnableThrowsException r) {
        milliTimer(r, "");
    }

    public static void nanoTimer(RunnableThrowsException r, String name) {
        printTimer(name, nano(r), 1);
    }
    public static void nanoTimer(RunnableThrowsException r) {
        nanoTimer(r, "");
    }

    private static long nano(RunnableThrowsException r) {
        long avg = 0L;
        for (int i = 0; i < times; i++) {
            long startTime = System.nanoTime();
            try {r.run();} catch (Exception e) {e.printStackTrace();}
            long endTime = System.nanoTime();
            avg = avg == 0 ? endTime - startTime : ((endTime - startTime) + avg) / 2;
        }
        return avg;
    }

    private static void printTimer(String name, long nano, double pow) {
        System.out.printf("%s%s耗时：%d%s秒\n",
                name,
                times != 1 ? format("调用%d次平均", times) : "",
                (long) (nano / Math.pow(10, pow)),
                pow == 1 ? "纳" : pow == 6 ? "毫" : "");
    }
}
