package com.test.ref;

import com.jmc.reference.Func;

public class FuncTest {
    public static void main(String[] args) {
        solve(Func.of(FuncTest::add), 1, 2);

        solve(Func.of((Integer a, Integer b) -> a - b), 2, 3);

        var m = Func.bind(FuncTest::add, 3, 4);
        int result = m.invoke();
        System.out.println(result);
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static void solve(Func<Integer> m, int a, int b) {
        int result = m.invoke(a, b);
        System.out.println(result);
    }
}
