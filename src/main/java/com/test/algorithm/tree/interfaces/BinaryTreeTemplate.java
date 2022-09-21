package com.test.algorithm.tree.interfaces;

public interface BinaryTreeTemplate<K extends Comparable<K>, V> {
    void put(K k, V v);
    V get(K k);
    void delete(K k);
    int size();
    K min();
    K max();
    int maxDepth();
}
