package com.test.juc.apply.pool;

public class Test {
    public static void main(String[] args) {
        var pool = new ThreadPool(3, new BlockingQueue<>(5), System.err::println);

        for (int i = 1; i <= 10; i++) {
            int j = i;
            // Thread.yield();
            pool.execute(() -> System.out.println(j));
        }

        pool.shutdown();
    }
}
