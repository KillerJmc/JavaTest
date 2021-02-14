package com.test.algorithm.tree.interfaces;

public interface PriorityQueueTemplate<T extends Comparable<T>> {
    default T delMax() {
        return null;
    }

    default T delMin() {
        return null;
    }

    void insert(T t);
    int size();
    boolean isEmpty();
}
