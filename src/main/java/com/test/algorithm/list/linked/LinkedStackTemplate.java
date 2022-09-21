package com.test.algorithm.list.linked;

public interface LinkedStackTemplate<T> {
    boolean isEmpty();
    int size();
    T pop();
    void push(T t);
}