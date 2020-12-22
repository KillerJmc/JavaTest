package com.test.algorithm.list.sequence.impl;


import static com.test.algorithm.utils.ArrayUtils.less;

public class SequenceSortedSymbolTable<K, V> extends SequenceSymbolTable<K, V> {
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void put(K k, V v) {
        if (N == capacity) resize(capacity * 2);
        for (int i = 0; i < N; i++) {
            if (keys[i].equals(k)) {
                values[i] = v;
                return;
            } else if (less((Comparable) k, (Comparable) keys[i])) {
                System.arraycopy(keys, i, keys, i + 1, N - i);
                System.arraycopy(values, i, values, i + 1, N - i);
                keys[i] = k;
                values[i] = v;
                N++;
                return;
            }
        }

        keys[N] = k;
        values[N] = v;
        N++;
    }
}
