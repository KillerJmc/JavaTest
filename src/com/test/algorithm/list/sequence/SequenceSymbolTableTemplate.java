package com.test.algorithm.list.sequence;

public interface SequenceSymbolTableTemplate<K, V> {
    void resize(int newSize);
    V get(K k);
    void put(K k, V v);
    void delete(K k);
    int size();
}
