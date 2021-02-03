package com.test.ref;

import com.jmc.reference.MethodRef;

public class MethodRefTest {
    public static void main(String[] args) {
        solve(MethodRef.of(MethodRefTest::add), 1, 2);

        solve(MethodRef.of((Integer a, Integer b) -> a - b), 2, 3);

        var m = MethodRef.bind(MethodRefTest::add, 3, 4);
        int result = m.invoke();
        System.out.println(result);
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static void solve(MethodRef<Integer> m, int a, int b) {
        int result = m.invoke(a, b);
        System.out.println(result);
    }
}
