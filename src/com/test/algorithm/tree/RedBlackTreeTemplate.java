package com.test.algorithm.tree;

public abstract class RedBlackTreeTemplate<K extends Comparable<K>, V> {
    protected static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> left;
        public Node<K, V> right;
        public boolean color;

        public Node(K key, V value, Node<K, V> left, Node<K, V> right, boolean color) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.color = color;
        }
    }

    public abstract int size();

    protected abstract boolean isRed(Node<K, V> x);

    protected abstract Node<K, V> rotateLeft(Node<K, V> h);

    protected abstract Node<K, V> rotateRight(Node<K, V> h);

    protected abstract void flipColors(Node<K, V> h);

    protected abstract Node<K, V> put(Node<K, V> h, K k, V v);

    public abstract void put(K k, V v);

    protected abstract V get(Node<K, V> x, K k);

    public abstract V get(K k);
}
