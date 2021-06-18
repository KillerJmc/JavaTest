package com.test.algorithm.list.linked.impl;

import com.test.algorithm.list.linked.SinglyLinkedListTemplate;

import java.util.Iterator;

public class SinglyLinkedList<T> implements SinglyLinkedListTemplate<T>, Iterable<T> {
    private final Node<T> head;
    private int N;

    public static class Node<T> {
        public T item;
        public Node<T> next;

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public String toString() {
            return item.toString();
        }
    }

    public SinglyLinkedList() {
        this.head = new Node<>(null, null);
        this.N = 0;
    }

    @Override
    public void clear() {
        head.next = null;
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
        Node<T> n = head;
        while (i-- >= 0) n = n.next;
        return n.item;
    }

    @Override
    public void insert(int i, T t) {
        Node<T> n = head;
        while (i-- > 0) n = n.next;
        n.next = new Node<>(t, n.next);
        N++;
    }

    @Override
    public void insert(T t) {
        Node<T> n = head;
        while (n.next != null) n = n.next;
        n.next = new Node<>(t, null);
        N++;
    }

    @Override
    public T remove(int i) {
        Node<T> n = head;
        while (i-- > 0) n = n.next;
        T removed = n.next.item;
        n.next = n.next.next;
        N--;
        return removed;
    }

    public void remove(T t) {
        remove(indexOf(t));
    }

    @Override
    public int indexOf(T t) {
        Node<T> n = head;
        for (int i = 0; n.next != null; i++) {
            if (n.next.item.equals(t)) return i;
            n = n.next;
        }

        return -1;
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

    public void reverse() {
        if (isEmpty()) return;
        reverse(head.next);
    }

    private Node<T> reverse(Node<T> curr) {
        if (curr.next == null) {
            head.next = curr;
            return curr;
        }

        var pre = reverse(curr.next);
        pre.next = curr;
        curr.next = null;
        return curr;
    }

    @SuppressWarnings("all")
    public T getMid() {
        Node<T> fast = head.next, slow = fast;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow.item;
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            T t = it.next();
            sb.append(t == this ? "(this Collection)" : t);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
