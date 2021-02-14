package com.test.algorithm.tree.impl;

import static com.jmc.array.Arrs.*;

public class Heap<T extends Comparable<T>> {
    private final T[] items;
    private int N;

    @SuppressWarnings("unchecked")
    public Heap(int capacity) {
        this.items = (T[]) new Comparable[capacity + 1];
        this.N = 0;
    }

    public T delMax() {
        if (N == 0) return null;
        T t = items[1];

        swap(items, 1, N);
        items[N--] = null;

        sink(1);
        return t;
    }

    public void insert(T t) {
        items[++N] = t;
        swim(N);
    }

    private void swim(int k) {
        for (; k / 2 != 0; k /= 2)
            if (greater(items, k, k / 2))
                swap(items, k, k / 2);
            else
                return;
    }

    @SuppressWarnings("all")
    private void sink(int k) {
        while (k * 2 <= N) {
            int maxIdx = k * 2 + 1 > N ? k * 2 : greater(items, k * 2, k * 2 + 1) ? k * 2 : k * 2 + 1;
            if (less(items, k, maxIdx))
                swap(items, k, k = maxIdx);
            else
                return;
        }
    }
}
