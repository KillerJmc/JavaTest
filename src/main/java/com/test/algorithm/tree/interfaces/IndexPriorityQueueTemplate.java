package com.test.algorithm.tree.interfaces;

public interface IndexPriorityQueueTemplate<T extends Comparable<T>> {
    default int delMin() {
        return -1;
    }

    default int delMax() {
        return -1;
    }

    default int maxIdx() {
        return -1;
    }

    default int minIdx() {
        return -1;
    }

    int size();
    boolean isEmpty();
    boolean contains(int k);
    void insert(int i, T t);
    void delete(int i);
}
