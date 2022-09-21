package com.test.algorithm.list.sequence.impl;

import com.test.algorithm.list.sequence.SequenceStackTemplate;

import java.util.Arrays;
import java.util.Iterator;

public class SequenceStack<T> implements SequenceStackTemplate<T>, Iterable<T> {
    private T[] eles;
    private int N;

    @SuppressWarnings("unchecked")
    public SequenceStack(int capacity) {
        this.eles = (T[]) new Object[capacity];
        this.N = 0;
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
    public T pop() {
        return eles[--N];
    }

    @Override
    public void push(T t) {
        if (N == eles.length) eles = Arrays.copyOf(eles, N * 2);
        eles[N++] = t;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private int cursor;

        public Itr() {
            cursor = N - 1;
        }

        @Override
        public boolean hasNext() {
            return cursor >= 0;
        }

        @Override
        public T next() {
            return eles[cursor--];
        }
    }
}
