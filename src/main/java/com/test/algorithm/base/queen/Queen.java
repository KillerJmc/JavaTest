package com.test.algorithm.base.queen;

public class Queen {
    private final int MAX = 8;
    private final int[] a;
    private int N;

    public Queen() {
        this.a = new int[MAX];
        this.N = 0;
        check(0);
    }

    private boolean judge(int k) {
        for (int i = 0; i < k; i++) {
            if (a[i] == a[k] || Math.abs(i - k) == Math.abs(a[i] - a[k])) return false;
        }
        return true;
    }

    /**
     * 放置第k - 1个皇后
     * @param k 新皇后的下标
     */
    private void check(int k) {
        if (k == MAX) {
            N++;
            print();
            return;
        }

        for (int i = 0; i < MAX; i++) {
            a[k] = i;
            if (judge(k)) check(k + 1);
        }
    }

    private void print() {
        for (var t : a) {
            for (int i = 0; i < MAX; i++) System.out.print((i != t ? 0 : 1) + "  ");
            System.out.println();
        }
        System.out.println("N = " + N + "\n");;
    }
}
