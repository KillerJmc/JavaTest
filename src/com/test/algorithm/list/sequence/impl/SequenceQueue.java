package com.test.algorithm.list.sequence.impl;

import com.test.algorithm.list.sequence.SequenceQueueTemplate;

import java.util.Arrays;
import java.util.Iterator;

public class SequenceQueue<T> implements SequenceQueueTemplate<T>, Iterable<T> {
    private T[] eles;
    private int N;

    @SuppressWarnings("unchecked")
    public SequenceQueue(int capacity) {
        eles = (T[]) new Object[capacity];
        N = 0;
    }

    public SequenceQueue() {
        this(10);
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public T poll() {
        T t = eles[0];
        System.arraycopy(eles, 1, eles, 0, --N);
        return t;
    }

    @Override
    public void add(T t) {
        if (N == eles.length) resize(N * 2);
        eles[N++] = t;
    }

    @Override
    public void resize(int newSize) {
        eles = Arrays.copyOf(eles, newSize);
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private int cursor;

        public Itr() {
            cursor = 0;
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
