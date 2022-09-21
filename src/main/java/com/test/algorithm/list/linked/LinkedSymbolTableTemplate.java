package com.test.algorithm.list.linked;

public interface LinkedSymbolTableTemplate<K, V> {
    V get(K k);
    void put(K k, V v);
    void delete(K k);
    int size();
}
