package com.test.interview.ref;

import java.lang.ref.WeakReference;

/**
 * 弱引用：很弱的引用，gc看到即回收
 */
public class WeakRefTest {
    public static void main(String[] args) {
        WeakReference<byte[]> m = new WeakReference<>(new byte[1024]);
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

    }
}
