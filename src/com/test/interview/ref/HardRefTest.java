package com.test.interview.ref;


/**
 * 强引用：很强的引用，只有没有任何引用指向它，才会被回收
 */
public class HardRefTest {
    public static void main(String[] args) {
        M m = new M();
        System.gc();
        System.out.println(m);
        m = null;
        System.gc();
    }
}