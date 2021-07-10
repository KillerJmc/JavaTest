package com.test.algorithm.list.linked.impl;


import static com.jmc.util.Compare.lt;

public class LinkedSortedSymbolTable<K extends Comparable<K>, V> extends LinkedSymbolTable<K, V> {
    @Override
    public void put(K k, V v) {
        Node<K, V> pre = head;

        var n = head;
        while ((n = n.next) != null) {
            if (n.key.equals(k)) {
                n.value = v;
                return;
            } else if (lt(k, n.key)) {
                pre.next = new Node<>(k, v, n);
                N++;
                return;
            }
            pre = n;
        }
        pre.next = new Node<>(k, v, null);
    }
}
