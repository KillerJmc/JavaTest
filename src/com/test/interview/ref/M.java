package com.test.interview.ref;

public class M {
    public String name = "I'm M";
    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() throws Throwable {
        System.out.println("finalize...");
    }
}

