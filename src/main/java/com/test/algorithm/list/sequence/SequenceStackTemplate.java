package com.test.algorithm.list.sequence;

public interface SequenceStackTemplate<T> {
    boolean isEmpty();
    int size();
    T pop();
    void push(T t);
}
