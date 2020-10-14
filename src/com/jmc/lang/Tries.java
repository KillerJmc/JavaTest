package com.jmc.lang;

public class Tries {
    public static void tryThis(RunnableThrowsException r) {
        try {
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Exception tryThrowsE(RunnableThrowsException r) {
        try {
            r.run();
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    public static<T> T tryReturnsT(RunnableThrowsAndReturn<T> r) {
        try {
            return r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
