package com.test.algorithm.tree.impl;

import com.test.algorithm.list.sequence.impl.SequenceQueue;
import com.test.algorithm.tree.BinaryTreeTemplate;
import com.test.algorithm.tree.TreeIterator;

import static com.test.algorithm.utils.ArrayUtils.greater;
import static com.test.algorithm.utils.ArrayUtils.less;

public class BinaryTree<K extends Comparable<K>, V> implements BinaryTreeTemplate<K, V>, TreeIterator<K> {
    private Node<K, V> root;
    private int N;

    private static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> left;
        public Node<K, V> right;

        public Node(K key, V value, Node<K, V> left, Node<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node() {

        }
    }

    public BinaryTree() {
        this.root = null;
        this.N = 0;
    }

    @Override
    public void put(K k, V v) {
        root = put(root, k, v);
        N++;
    }

    private Node<K, V> put(Node<K, V> x, K k, V v) {
        if (x == null) return new Node<>(k, v, null, null);

        if (less(k, x.key)) {
            x.left = put(x.left, k, v);
        } else if (greater(k, x.key)) {
            x.right = put(x.right, k, v);
        } else {
            x.value = v;
            N--;
        }
        return x;
    }

    @Override
    public V get(K k) {
        return get(root, k);
    }

    private V get(Node<K, V> x, K k) {
        return x == null ? null :
            less(k, x.key) ? get(x.left, k) :
            greater(k, x.key) ? get(x.right, k) : x.value;
    }

    @Override
    public void delete(K k) {
        root = delete(root, k);
    }

    private Node<K, V> delete(Node<K, V> x, K k) {
        if (x == null) return null;

        if (less(k, x.key)) {
            x.left = delete(x.left, k);
        } else if (greater(k, x.key)) {
            x.right = delete(x.right, k);
        } else {
            N--;
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;

            var minNode = x.right;
            while (minNode.left != null) minNode = minNode.left;

            // 删除父树对minNode的引用
            var n = x.right;
            if (n.left == null) {
                x.right = null;
            } else {
                while (n.left != null)
                    if (n.left.left == null) n.left = null;
            }

            minNode.left = x.left;
            minNode.right = x.right;
            x.left = x.right = null;
            x = minNode;
        }
        return x;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public K min() {
        return min(root).key;
    }

    private Node<K, V> min(Node<K, V> x) {
        return x == null ? new Node<>()
            : x.left != null ? min(x.left) : x;
    }

    @Override
    public K max() {
        return max(root).key;
    }

    private Node<K, V> max(Node<K, V> x) {
        return x == null ? new Node<>()
            : x.right != null ? max(x.right) : x;
    }


    @Override
    public SequenceQueue<K> preErgodic() {
        var keys = new SequenceQueue<K>();
        preErgodic(root, keys);
        return keys;
    }

    private void preErgodic(Node<K, V> x, SequenceQueue<K> keys) {
        if (x == null) return;

        keys.add(x.key);

        if (x.left != null) preErgodic(x.left, keys);

        if (x.right != null) preErgodic(x.right, keys);
    }

    @Override
    public SequenceQueue<K> midErgodic() {
        var keys = new SequenceQueue<K>();
        midErgodic(root, keys);
        return keys;
    }

    private void midErgodic(Node<K, V> x, SequenceQueue<K> keys) {
        if (x == null) return;

        if (x.left != null) midErgodic(x.left, keys);

        keys.add(x.key);

        if (x.right != null) midErgodic(x.right, keys);
    }

    @Override
    public SequenceQueue<K> afterErgodic() {
        var keys = new SequenceQueue<K>();
        afterErgodic(root, keys);
        return keys;
    }

    private void afterErgodic(Node<K, V> x, SequenceQueue<K> keys) {
        if (x == null) return;

        if (x.left != null) afterErgodic(x.left, keys);

        if (x.right != null) afterErgodic(x.right, keys);

        keys.add(x.key);
    }

    @Override
    public SequenceQueue<K> laserErgodic() {
        if (root == null) return new SequenceQueue<>();

        var keys = new SequenceQueue<K>();
        var assist = new SequenceQueue<Node<K, V>>();
        assist.add(root);

        while (!assist.isEmpty()) {
            var n = assist.poll();
            keys.add(n.key);
            if (n.left != null) assist.add(n.left);
            if (n.right != null) assist.add(n.right);
        }
        return keys;
    }

    @Override
    public int maxDepth() {
        return maxDepth(root);
    }

    private int maxDepth(Node<K, V> x) {
        if (x == null) return 0;

        int maxL = 0, maxR = 0;
        if (x.left != null) maxL = maxDepth(x.left);
        if (x.right != null) maxR = maxDepth(x.right);

        // 包括每个根节点
        return Math.max(maxL, maxR) + 1;
    }
}
