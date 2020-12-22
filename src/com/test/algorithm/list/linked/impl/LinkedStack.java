package com.test.algorithm.list.linked.impl;

import com.test.algorithm.list.linked.LinkedStackTemplate;

import java.util.Iterator;

public class LinkedStack<T> implements LinkedStackTemplate<T>, Iterable<T> {
    private final Node<T> head;
    private int N;

    private static class Node<T> {
        public T item;
        public Node<T> next;

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }

    public LinkedStack() {
        head = new Node<>(null, null);
        N = 0;
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
    public T pop() {
        if (isEmpty()) return null;
        T result = head.next.item;
        head.next = head.next.next;
        N--;
        return result;
    }

    @Override
    public void push(T t) {
        var n = head.next;
        head.next = new Node<>(t, n);
        N++;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder("[");
        var it = iterator();

        while (it.hasNext())
            sb.append(it.next()).append(it.hasNext() ? ", " : "]");

        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        private Node<T> n;

        public Itr() {
            this.n = head;
        }

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
