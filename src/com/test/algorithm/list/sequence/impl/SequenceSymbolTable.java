package com.test.algorithm.list.sequence.impl;

import com.test.algorithm.list.sequence.SequenceSymbolTableTemplate;

import java.util.Arrays;
import java.util.function.BiConsumer;

public class SequenceSymbolTable<K, V> implements SequenceSymbolTableTemplate<K, V> {
    protected K[] keys;
    protected V[] values;
    protected int N;
    protected int capacity;

    @SuppressWarnings("unchecked")
    public SequenceSymbolTable() {
        this.N = 0;
        this.capacity = 0;
        this.keys = (K[]) new Object[0];
        this.values = (V[]) new Object[0];
    }

    @Override
    public void resize(int newSize) {
        if (newSize == 0) newSize = 10;
        keys = Arrays.copyOf(keys, newSize);
        values = Arrays.copyOf(values, newSize);
        capacity = newSize;
    }

    @Override
    public V get(K k) {
        for (int i = 0; i < N; i++)
            if (keys[i].equals(k)) return values[i];
        return null;
    }

    @Override
    public void put(K k, V v) {
        if (N == capacity) resize(capacity * 2);
        for (int i = 0; i < N; i++) {
            if (keys[i].equals(k)) {
                values[i] = v;
                return;
            }
        }

        keys[N] = k;
        values[N] = v;
        N++;
    }

    @Override
    public void delete(K k) {
        for (int i = 0; i < N; i++) {
            if (keys[i].equals(k)) {
                System.arraycopy(keys, i + 1, keys, i, N - i - 1);
                System.arraycopy(values, i + 1, values, i, N - i - 1);
                N--;
                return;
            }
        }
    }

    @Override
    public int size() {
        return N;
    }

    public void forEach(BiConsumer<K, V> action) {
        for (int i = 0; i < N; i++)
            action.accept(keys[i], values[i]);
    }
}
