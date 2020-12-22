package com.test.algorithm.list.linked;

public interface SinglyLinkedListTemplate<T> {
    void clear();
    boolean isEmpty();
    int size();
    T get(int i);
    void insert(int i, T t);
    void insert(T t);
    T remove(int i);
    int indexOf(T t);
}
