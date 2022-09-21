package com.test.algorithm.list.sequence;

public interface SequenceListTemplate<T> {
    void clear();
    boolean isEmpty();
    int size();

    /**
     * 找到对应下标的元素
     * 时间复杂度O(1)
     * @param i 对应下标
     * @return 对应下标的元素
     */
    T get(int i);

    /**
     * 插入元素
     * 时间复杂度O(1)
     * @param t 插入的元素
     */
    void insert(T t);

    /**
     * 指定下标插入元素
     * 时间复杂度O(n)
     * @param i 指定下标
     * @param t 插入的元素
     */
    void insert(int i, T t);

    /**
     * 移除指定下标元素
     * 时间复杂度O(n)
     * @param i 指定下标
     */
    T remove(int i);

    int indexOf(T t);
    int capacity();
    void resize(int newSize);
}
