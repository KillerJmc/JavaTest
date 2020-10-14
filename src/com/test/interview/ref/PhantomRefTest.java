package com.test.interview.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class PhantomRefTest {
    public static void main(String[] args) {
        ReferenceQueue<M> queue = new ReferenceQueue<>();
        PhantomReference<M> m = new PhantomReference<>(new M(), queue);
        System.out.println(m.get());
    }
}
