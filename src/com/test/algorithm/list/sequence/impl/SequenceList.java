package com.test.algorithm.list.sequence.impl;

import com.test.algorithm.list.sequence.SequenceListTemplate;

import java.util.Arrays;
import java.util.Iterator;

public class SequenceList<T> implements SequenceListTemplate<T>, Iterable<T> {
    /**
     * 储存元素的数组
     */
    private T[] eles;
    /**
     * 当前线性表的长度
     */
    private int N;

    @SuppressWarnings("unchecked")
    public SequenceList(int capacity) {
        this.eles = (T[]) new Object[capacity];
        this.N = 0;
    }

    @Override
    public void clear() {
        this.N = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.N == 0;
    }

    @Override
    public int size() {
        return this.N;
    }

    @Override
    public T get(int i) {
        return eles[i];
    }

    @Override
    public void insert(T t) {
        // 如果插入后元素个数大于数组最大容量就扩容
        if (N + 1 > eles.length) resize(eles.length * 2);
        eles[N++] = t;
    }

    @Override
    public void insert(int i, T t) {
        // 如果插入后元素个数大于数组最大容量就扩容
        if (N == eles.length) resize(eles.length * 2);

        System.arraycopy(eles, i, eles, i + 1, N++ - i);
        eles[i] = t;
    }

    @Override
    public T remove(int i) {
        T t = eles[i];
        System.arraycopy(eles, i + 1, eles, i, N-- - i - 1);

        // 如果移除后元素个数小于数组最大容量就缩容
        if (N < eles.length / 4) resize(eles.length / 2);
        return t;
    }

    @Override
    public int indexOf(T t) {
        for (int i = 0; i < N; i++)
            if (eles[i].equals(t))
                return i;
        return -1;
    }

    @Override
    public int capacity() {
        return eles.length;
    }

    @Override
    public void resize(int newSize) {
        eles = Arrays.copyOf(eles, newSize);
    }

    public Object[] getEles() {
        return Arrays.copyOf(eles, N);
    }

    @Override
    public Iterator<T> iterator() {
        return new SLIterator();
    }

    private class SLIterator implements Iterator<T> {
        private int cursor;

        public SLIterator() {
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor < N;
        }

        @Override
        public T next() {
            return eles[cursor++];
        }
    }
}
