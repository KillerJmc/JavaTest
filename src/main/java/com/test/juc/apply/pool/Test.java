package com.test.juc.apply.pool;

import com.jmc.lang.Threads;
import com.test.main.Tools;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        var pool = new ThreadPool(3, new BlockingQueue<>(5), System.err::println);

        for (int i = 1; i <= 10; i++) {
            int j = i;
            pool.execute(() -> {
                Threads.sleep(1000);
                System.out.println(j);
            });
        }

        pool.shutdown();

        while (!pool.awaitTermination(500, TimeUnit.MILLISECONDS)) {
            System.err.println("正在等待结束！");
        }
        System.err.println("已结束！");
    }
}
