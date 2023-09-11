package com.test.main;

import com.jmc.io.Files;
import com.jmc.lang.Threads;
import com.jmc.lang.Tries;
import com.test.juc.apply.MyExchanger;
import com.test.utils.Base64Utils;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Jmc
 */
public class Main {
    @Test
    public void main() throws Exception {

        var exch = new MyExchanger<String>();

        new Thread(() -> {
            System.out.println("线程1开始执行");
            var data = Tries.tryReturnsT(() -> exch.exchange("t1哦！"));
            System.out.println("线程1收到数据：" + data);
            data = Tries.tryReturnsT(() -> exch.exchange("666哦！"));
            System.out.println("线程1收到数据：" + data);
        }).start();

        new Thread(() -> {
            System.out.println("线程2开始执行");
            var data = Tries.tryReturnsT(() -> exch.exchange("t2哦！"));
            System.out.println("线程2收到数据：" + data);
            data = Tries.tryReturnsT(() -> exch.exchange(null));
            System.out.println("线程2收到数据：" + data);
        }).start();

        Threads.sleep(3, TimeUnit.SECONDS);
    }
}


