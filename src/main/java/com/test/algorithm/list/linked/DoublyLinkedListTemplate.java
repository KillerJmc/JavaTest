package com.test.algorithm.list.linked;

public interface DoublyLinkedListTemplate<T> extends Iterable<T> {
    void clear();
    boolean isEmpty();
    int size();

    /**
     * 获得指定下标元素
     * 时间复杂度O(n)
     * @param i 指定的下标
     * @return 指定下标元素
     */
    T get(int i);

    /**
     * 插入元素到指定下标
     * 时间复杂度O(n)，但与顺序表相比不需要移动元素，速度更快
     * @param i 指定的下标
     * @param t 插入的元素
     */
    void insert(int i, T t);
    void insert(T t);

    /**
     * 删除指定下标元素
     * 时间复杂度O(n)，但与顺序表相比不需要移动元素，速度更快
     * @param i 指定的下标
     * @return 删除的元素
     */
    T remove(int i);
    int indexOf(T t);
    T getFirst();
    T getLast();
}
