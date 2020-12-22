package com.test.algorithm.list.sequence;

public interface SequenceQueueTemplate<T> {
    boolean isEmpty();
    int size();
    T poll();
    void add(T t);
    void resize(int newSize);
}
