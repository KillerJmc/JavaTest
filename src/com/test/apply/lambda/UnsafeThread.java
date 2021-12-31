package com.test.apply.lambda;

import com.jmc.lang.Threads;
import com.jmc.lang.Tries;

import java.util.stream.Stream;

public class UnsafeThread {
    static int i = 0;

    public static void main(String[] args) {
        Runnable r = () -> {
            Threads.sleep(10);
            i++;
        };

        Stream.generate(() -> r)
                .parallel()
                .map(Thread::new)
                .limit(100)
                .peek(Thread::start)
                .forEach(Tries.throwsE(Thread::join));

        System.out.println(i);
    }
}
