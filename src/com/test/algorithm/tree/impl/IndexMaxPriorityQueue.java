package com.test.algorithm.tree.impl;

import com.jmc.array.Arrs;
import com.test.algorithm.tree.interfaces.IndexPriorityQueueTemplate;

import java.util.Arrays;

public class IndexMaxPriorityQueue<T extends Comparable<T>> implements IndexPriorityQueueTemplate<T> {
    private final T[] items;
    private int N;
    private final int[] pq;
    private final int[] qp;

    @SuppressWarnings("unchecked")
    public IndexMaxPriorityQueue(int capacity) {
        this.items = (T[]) new Comparable[capacity];
        this.N = 0;
        this.pq = new int[capacity + 1];
        this.qp = new int[capacity];
        Arrays.fill(this.qp, -1);
    }

    private boolean less(int idx1, int idx2) {
        return Arrs.less(items, pq[idx1], pq[idx2]);
    }

    private boolean greater(int idx1, int idx2) {
        return Arrs.greater(items, pq[idx1], pq[idx2]);
    }

    private void swap(int idx1, int idx2) {
        Arrs.swap(qp, pq[idx1], pq[idx2]);
        Arrs.swap(pq, idx1, idx2);
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


    @Override
    public int maxIdx() {
        return isEmpty() ? -1 : pq[1];
    }

    @Override
    public boolean contains(int k) {
        if (k >= items.length) throw new IllegalArgumentException("索引k超出队列最大容量范围！");
        return qp[k] != -1;
    }

    @Override
    public int delMax() {
        if (isEmpty()) return -1;

        int maxIdx = maxIdx();
        items[maxIdx] = null;

        swap(1, N);
        qp[pq[N]] = -1;
        pq[N] = -1;

        N--;
        sink(1);
        return maxIdx;
    }

    @Override
    public void insert(int i, T t) {
        if (i >= items.length) throw new IllegalArgumentException("索引i超出队列最大容量范围！");

        items[i] = t;
        if (!contains(i)) {
            pq[++N] = i;
            qp[i] = N;
            swim(N);
        } else {
            sink(qp[i]);
            swim(qp[i]);
        }
    }

    @Override
    public void delete(int i) {
        if (i >= items.length) throw new IllegalArgumentException("索引i超出队列最大容量范围！");
        if (!contains(i)) return;

        items[i] = null;
        int preIdx = qp[i];

        swap(qp[i], N);
        pq[N] = -1;
        qp[i] = -1;

        N--;
        sink(preIdx);
    }

    private void swim(int k) {
        for (; k / 2 >= 1; k /= 2) {
            if (greater(k, k / 2))
                swap(k, k / 2);
            else
                return;
        }
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int maxIdx = k * 2 + 1 > N ? k * 2 : greater(k * 2, k * 2 + 1) ? k * 2 : k * 2 + 1;
            if (less(k, maxIdx))
                swap(k, k = maxIdx);
            else
                return;
        }
    }
}
