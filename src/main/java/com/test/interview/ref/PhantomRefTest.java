package com.test.interview.ref;

import com.jmc.lang.Threads;
import com.test.main.Tools;
import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.nio.ByteBuffer;

/**
 * 虚引用：不影响对象的生命周期，可以用来监控对象是否存活
 */
public class PhantomRefTest {
    @Test
    public void testRef() {
        //VM options: -Xmx20M
        //jdk version: 11
        M m = new M();
        ReferenceQueue<M> queue = new ReferenceQueue<>();
        PhantomReference<M> pr = new PhantomReference<>(m, queue);
        System.out.println(pr.get());
        System.out.println(m);
        System.out.println(m.name);

        byte[] bs = new byte[1024 * 1024 * 5];
        Threads.sleep(1000);
        byte[] bs2 = new byte[1024 * 1024 * 10];

        new Thread(() -> {
            while (true) {
                Reference<? extends M> poll = queue.poll();
                if (poll != null) System.out.println("M对象被回收了，引用对象：" + poll);
                Threads.sleep(500);
            }
        }).start();
    }

    /**
     * ByteBuffer.allocateDirect(1024)申请了一块堆外内存
     * 通过虚引用进行对byteBuffer的监控，当它被回收时，
     * 通过native方法（调用Hotspot）顺便手动释放掉堆外内存。
     */
    @Test
    public void testDirectByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
    }


}
