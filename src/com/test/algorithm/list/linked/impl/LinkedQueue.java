package com.test.algorithm.list.linked.impl;

import com.test.algorithm.list.linked.LinkedQueueTemplate;

import java.util.Iterator;

public class LinkedQueue<T> implements LinkedQueueTemplate<T>, Iterable<T> {
    private final Node<T> head;
    private Node<T> last;
    private int N;

    private static class Node<T> {
        public T item;
        public Node<T> next;

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }

    public LinkedQueue() {
        this.head = new Node<>(null, null);
        this.last = null;
        this.N = 0;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public T poll() {
        if (isEmpty()) return null;

        // 前面取
        T t = head.next.item;
        head.next = head.next.next;

        N--;
        return t;
    }

    @Override
    public void add(T t) {
        if (N++ == 0)
            head.next = last = new Node<>(t, null);
        else
            // 插后面
            last = last.next = new Node<>(t, null);
    }

    @Override
    public void clear() {
        head.next = last = null;
        N = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        Node<T> n = head;
        @Override
        public boolean hasNext() {
            return n.next != null;
        }

        @Override
        public T next() {
            return (n = n.next).item;
        }
    }
}
