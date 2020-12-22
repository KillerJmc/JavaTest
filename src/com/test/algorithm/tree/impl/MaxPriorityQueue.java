package com.test.algorithm.tree.impl;

import com.test.algorithm.tree.PriorityQueueTemplate;

import static com.test.algorithm.utils.ArrayUtils.*;

public class MaxPriorityQueue<T extends Comparable<T>> implements PriorityQueueTemplate<T> {
    private final T[] items;
    private int N;

    @SuppressWarnings("unchecked")
    public MaxPriorityQueue(int capacity) {
        this.items = (T[]) new Comparable[capacity + 1];
        this.N = 0;
    }

    @Override
    public T delMax() {
        if (N == 0) return null;

        T t = items[1];
        swap(items, 1, N);

        items[N--] = null;
        sink(1);

        return t;
    }

    @Override
    public void insert(T t) {
        items[++N] = t;
        swim(N);
    }

    private void swim(int k) {
        for (; k / 2 >= 1; k /= 2) {
            if (greater(items[k], items[k / 2]))
                swap(items, k, k / 2);
            else
                return;
        }
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int maxIdx = k * 2 + 1 > N ? k * 2 : greaterIdx(items, k * 2, k * 2 + 1);
            if (less(items[k], items[maxIdx]))
                swap(items, k, k = maxIdx);
            else
                return;
        }
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }
}
