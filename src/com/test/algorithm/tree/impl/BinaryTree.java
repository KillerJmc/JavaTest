package com.test.algorithm.tree.impl;

import java.util.ArrayList;
import java.util.LinkedList;

public class BinaryTree<K extends Comparable<K>, V> {
    private Node<K, V> root;
    private int size;

    public static final class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> left;
        public Node<K, V> right;
        public Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent, Node<K, V> left, Node<K, V> right) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "(" + key + " -> " + value + ")";
        }
    }

    public BinaryTree() {
        this.root = null;
        this.size = 0;
    }

    public void put(K k, V v) {
        size++;
        if (root == null) {
            root = new Node<>(k, v);
            return;
        }

        var n = root;
        while (true) {
            if (k.compareTo(n.key) > 0) {
                if (n.right != null) {
                    n = n.right;
                } else {
                    n.right = new Node<>(k, v, n);
                    return;
                }
            } else if (k.compareTo(n.key) < 0) {
                if (n.left != null) {
                    n = n.left;
                } else {
                    n.left = new Node<>(k, v, n);
                    return;
                }
            } else {
                n.value = v;
                size--;
                return;
            }
        }
    }

    public V remove(K k) {
        var n = root;
        var oldValue = n.value;

        while (n != null) {
            if (k.compareTo(n.key) > 0) {
                n = n.right;
            } else if (k.compareTo(n.key) < 0) {
                n = n.left;
            } else {
                if (n.left == null) {
                    coverAdjacentNode(n, n.right);
                } else if (n.right == null) {
                    coverAdjacentNode(n, n.left);
                } else {
                    // 后继节点
                    var nextNode = n.right;
                    while (nextNode.left != null) nextNode = nextNode.left;
                    n.key = nextNode.key;
                    n.value = nextNode.value;

                    coverAdjacentNode(nextNode, nextNode.right);
                }
                size--;
                return oldValue;
            }
        }

        return null;
    }

    private void coverAdjacentNode(Node<K, V> n, Node<K, V> target) {
        if (target != null) target.parent = n.parent;

        if (n == root) {
            root = target;
            n.left = n.right = null;
            return;
        }

        if (n.parent.right == n) {
            n.parent.right = target;
        } else {
            n.parent.left = target;
        }

        n.parent = n.left = n.right = null;
    }


    public V get(K k) {
        var n = root;
        while (n != null) {
            if (k.compareTo(n.key) > 0) {
                n = n.right;
            } else if (k.compareTo(n.key) < 0) {
                n = n.left;
            } else {
                return n.value;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public TreeIterator<K, V> iterator() {
        return new TreeIterator<>(root);
    }

    @SuppressWarnings("all")
    public static class TreeIterator<K, V> {
        private final Node<K, V> root;

        private TreeIterator(Node<K, V> root) {
            this.root = root;
        }

        public ArrayList<Node<K, V>> laser() {
            var resultList = new ArrayList<Node<K, V>>();
            var queue = new LinkedList<Node<K, V>>();
            queue.add(root);
            while (!queue.isEmpty()) {
                var n = queue.poll();
                resultList.add(n);
                if (n.left != null) queue.add(n.left);
                if (n.right != null) queue.add(n.right);
            }
            return resultList;
        }

        public ArrayList<Node<K, V>> midOrder() {
            var resultList = new ArrayList<Node<K, V>>();
            midOrder(root, resultList);
            return resultList;
        }

        private void midOrder(Node<K, V> n, ArrayList<Node<K, V>> resultList) {
            if (n.left != null) midOrder(n.left, resultList);
            resultList.add(n);
            if (n.right != null) midOrder(n.right, resultList);
        }

        public ArrayList<Node<K, V>> preOrder() {
            var resultList = new ArrayList<Node<K, V>>();
            preOrder(root, resultList);
            return resultList;
        }

        private void preOrder(Node<K, V> n, ArrayList<Node<K, V>> resultList) {
            resultList.add(n);
            if (n.left != null) preOrder(n.left, resultList);
            if (n.right != null) preOrder(n.right, resultList);
        }

        public ArrayList<Node<K, V>> postOrder() {
            var resultList = new ArrayList<Node<K, V>>();
            postOrder(root, resultList);
            return resultList;
        }

        private void postOrder(Node<K, V> n, ArrayList<Node<K, V>> resultList) {
            if (n.left != null) postOrder(n.left, resultList);
            if (n.right != null) postOrder(n.right, resultList);
            resultList.add(n);
        }
    }
}