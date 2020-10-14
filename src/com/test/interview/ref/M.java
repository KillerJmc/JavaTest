package com.test.interview.ref;

public class M {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize...");
    }
}

