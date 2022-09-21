package com.test.algorithm.list.linked.impl;

import com.test.algorithm.list.linked.DoublyLinkedListTemplate;

import java.util.Iterator;

public class DoublyLinkedList<T> implements DoublyLinkedListTemplate<T>, Iterable<T> {
    private final Node<T> head;
    private Node<T> last;
    private int N;

    public DoublyLinkedList() {
        this.head = new Node<>(null, null, null);
        this.last = null;
        this.N = 0;
    }

    public static class Node<T> {
        public T item;
        public Node<T> pre;
        public Node<T> next;

        public Node(T item, Node<T> pre, Node<T> next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    ", pre=" + pre.item +
                    ", next=" + next.item +
                    '}';
        }
    }

    @Override
    public void clear() {
        head.next = last = null;
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
    public T get(int i) {
        Node<T> n;
        if (i + 1 > N / 2) {
            n = last;
            for (int k = 0; k < N - i - 1; k++) n = n.pre;
        } else {
            n = head;
            while (i-- >= 0) n = n.next;
        }
        return n.item;
    }

    @Override
    public void insert(int i, T t) {
        if (i + 1 > N / 2) {
            var n = last;
            for (int k = 0; k < N - i - 1; k++) n = n.pre;
            var newNode = new Node<>(t, n.pre, n);
            n.pre.next = newNode;
            n.pre = newNode;
        } else {
            var n = head;
            while (i-- > 0) n = n.next;
            var newNode = new Node<>(t, n, n.next);
            n.next.pre = newNode;
            n.next = newNode;
        }
        N++;
    }

    @Override
    public void insert(T t) {
        last = N++ == 0 ?
                (head.next = new Node<>(t, head, null)) : (last.next = new Node<>(t, last, null));
    }

    @Override
    public T remove(int i) {
        Node<T> n;
        T removed;
        if (i + 1 > N / 2) {
            n = last;
            for (int k = 0; k < N - i - 1; k++) n = n.pre;
        } else {
            n = head;
            while (i-- >= 0) n = n.next;
        }

        removed = n.item;
        n.pre.next = n.next;
        if (n.next != null) n.next.pre = n.pre;

        N--;
        return removed;
    }

    @Override
    public int indexOf(T t) {
        var n = head;
        for (int i = 0; n.next != null; i++)
            if ((n = n.next).item.equals(t))
                return i;

        return -1;
    }

    @Override
    public T getFirst() {
        return N == 0 ? null : head.next.item;
    }

    @Override
    public T getLast() {
        return N == 0 ? null : last.item;
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

    @SuppressWarnings("all")
    public void reverse() {
        if (N == 0) return;
        Node<T> first = head.next, n = head.next;

        while (n != null) {
            var nextNode = n.next;
            n.next = n.pre;
            n.pre = nextNode;
            n = nextNode;
        }

        first.next = null;

        head.next = last;

        last.pre = head;
        last = first;
    }
}
