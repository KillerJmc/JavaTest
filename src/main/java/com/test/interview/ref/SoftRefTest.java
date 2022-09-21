package com.test.interview.ref;

import com.test.main.Tools;

import java.lang.ref.SoftReference;

/**
 * 软引用：很软的引用，内存足够时不会被gc回收，内存不足时直接被回收
 */
public class SoftRefTest {
    public static void main(String[] args) {
        //VM options: -Xmx20M
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(m.get());
        //提醒gc来回收内存
        System.gc();
        Tools.sleep(500);
        System.out.println(m.get());

        byte[] b = new byte[1024 * 1024 * 15];
        System.out.println(m.get());
    }
}
