package com.test.interview.ref;

import com.jmc.lang.Threads;
import com.test.main.Tools;
import org.junit.Test;

import java.lang.ref.WeakReference;

/**
 * 弱引用：很弱的引用，gc看到即回收
 */
public class WeakRefTest {
    @Test
    public void testRef() {
        WeakReference<byte[]> m = new WeakReference<>(new byte[1024]);
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());
    }

    //线程池慎用！可能会忘记remove导致内存泄露，得到上次设的值，甚至造成内存溢出
    /**
     * ThreadLocal用于在线程中储存数据和同一线程下获取同个对象
     * 底层Map的Entry的key用到了弱引用以防止内存泄露
     */
    ThreadLocal<M> tl = new ThreadLocal<>();
    @Test
    public void testThreadLocal() {
        new Thread(() -> {
            tl.set(new M());
            //干掉map中的tl为k，value为v的条目。
            //防止内存泄露！
//            tl.remove();
        }).start();
        Threads.sleep(500);
        new Thread(() -> System.out.println(tl.get())).start();
        /**
         * junit执行完所有test方法后会直接用System.exit();
         * 不会等待其他线程
         */
        Threads.sleep(500);
    }
}
