package com.jmc.lang.extend;

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

    public interface RunnableThrowsAndReturn<T> {
        T run() throws Exception;
    }

    public interface RunnableThrowsException {
        void run() throws Exception;
    }
}
