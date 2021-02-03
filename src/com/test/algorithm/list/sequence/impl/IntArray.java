package com.test.algorithm.list.sequence.impl;

import java.util.Arrays;
import java.util.function.IntConsumer;

public class IntArray {
    /**
     * 储存元素的数组
     */
    private int[] a;
    /**
     * 当前线性表的长度
     */
    private int N;

    public IntArray() {
        this(1);
    }

    public IntArray(int capacity) {
        this.a = new int[capacity];
        this.N = 0;
    }

    public void clear() {
        this.N = 0;
    }

    public boolean isEmpty() {
        return this.N == 0;
    }

    public int size() {
        return this.N;
    }

    public int get(int i) {
        return a[i];
    }

    public void insert(int t) {
        // 如果插入后元素个数大于数组最大容量就扩容
        if (N + 1 > a.length) resize(a.length * 2);
        a[N++] = t;
    }

    public void insert(int i, int t) {
        // 如果插入后元素个数大于数组最大容量就扩容
        if (N == a.length) resize(a.length * 2);

        System.arraycopy(a, i, a, i + 1, N++ - i);
        a[i] = t;
    }

    public int remove(int i) {
        int t = a[i];
        System.arraycopy(a, i + 1, a, i, N-- - i - 1);

        // 如果移除后元素个数小于数组最大容量就缩容
        if (N < a.length / 4) resize(a.length / 2);
        return t;
    }

    public int indexOf(int t) {
        for (int i = 0; i < N; i++)
            if (a[i]== t)
                return i;
        return -1;
    }

    public int capacity() {
        return a.length;
    }

    public void resize(int newSize) {
        a = Arrays.copyOf(a, newSize);
    }

    public int[] getArray() {
        return Arrays.copyOf(a, N);
    }

    public void forEach(IntConsumer c) {
        for (int i = 0; i < N; i++) c.accept(a[i]);
    }
}
