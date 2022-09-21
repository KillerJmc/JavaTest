package com.test.algorithm.tree.impl;

import com.test.algorithm.tree.interfaces.RedBlackTreeTemplate;

import static com.jmc.util.Compare.gt;
import static com.jmc.util.Compare.lt;

public class RedBlackTree<K extends Comparable<K>, V> extends RedBlackTreeTemplate<K, V> {;
    private Node<K, V> root;
    private int N;
    public static final boolean RED = true;
    public static final boolean BLACK = false;

    @Override
    public int size() {
        return N;
    }

    /**
     * 判断指向当前节点的链接是否为红色
     * 空连接默认为黑色
     * @param x 当前节点
     * @return 指向当前节点的链接是否为红色
     */
    @Override
    protected boolean isRed(Node<K, V> x) {
        return x != null && x.color == RED;
    }

    @Override
    protected Node<K, V> rotateLeft(Node<K, V> h) {
        var x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    @Override
    protected Node<K, V> rotateRight(Node<K, V> h) {
        var x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    @Override
    protected void flipColors(Node<K, V> h) {
        h.left.color = h.right.color = BLACK;
        h.color = RED;
    }

    @Override
    protected Node<K, V> put(Node<K, V> h, K k, V v) {
        if (h == null) return new Node<>(k, v, null, null, RED);

        if (lt(k, h.key))
            h.left = put(h.left, k, v);
        else if (gt(k, h.key))
            h.right = put(h.right, k, v);
        else
            h.value = v;

        if (!isRed(h.left) && isRed(h.right)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    @Override
    public void put(K k, V v) {
        root = put(root, k, v);
        N++;
        // 根节点的颜色总是红色的
        root.color = BLACK;
    }

    @Override
    protected V get(Node<K, V> x, K k) {
        return x == null ? null :
            lt(k, x.key) ? get(x.left, k) :
            gt(k, x.key) ? get(x.right, k) : x.value;
    }

    @Override
    public V get(K k) {
        return get(root, k);
    }
}
