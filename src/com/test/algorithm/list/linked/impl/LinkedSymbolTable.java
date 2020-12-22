package com.test.algorithm.list.linked.impl;

import com.test.algorithm.list.linked.LinkedSymbolTableTemplate;

import java.util.function.BiConsumer;

public class LinkedSymbolTable<K, V> implements LinkedSymbolTableTemplate<K, V> {
    protected final Node<K, V> head;
    protected int N;

    protected static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public LinkedSymbolTable() {
        this.head = new Node<>(null, null, null);
        this.N = 0;
    }

    @Override
    public V get(K k) {
        var n = head;
        while ((n = n.next) != null)
            if (n.key.equals(k)) return n.value;
        return null;
    }

    @Override
    public void put(K k, V v) {
        Node<K, V> pre = head;

        var n = head;
        while ((n = n.next) != null) {
            if (n.key.equals(k)) {
                n.value = v;
                return;
            }
            pre = n;
        }
        pre.next = new Node<>(k, v, null);
        N++;
    }

    @Override
    public void delete(K k) {
        var n = head;
        while (n != null) {
            var pre = n;
            if ((n = n.next) != null && n.key.equals(k)) {
                pre.next = n.next;
                N--;
                return;
            }
        }
    }

    @Override
    public int size() {
        return N;
    }

    public void forEach(BiConsumer<K, V> action) {
        var n = head;
        while ((n = n.next) != null) {
            action.accept(n.key, n.value);
        }
    }
}
