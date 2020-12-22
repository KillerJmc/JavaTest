package com.test.algorithm.list.linked;

public interface LinkedQueueTemplate<T> {
    boolean isEmpty();
    int size();
    T poll();
    void add(T t);
    void clear();
}
