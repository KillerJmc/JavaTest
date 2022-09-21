package com.test.algorithm.tree.impl;

import java.util.PriorityQueue;

/**
 * 赫夫曼树是wpl（weight path length)最短的树
 */
public class HuffmanTree {
    public static Node create(int[] arr) {
        var queue = new PriorityQueue<Node>();
        for (var t : arr) queue.add(new Node(t));

        while (queue.size() != 1) {
            Node left = queue.poll(), right = queue.poll();
            assert left != null && right != null;
            Node parent = new Node(left.value + right.value, left, right);
            queue.add(parent);
        }

        return queue.poll();
    }

    private static record Node(int value, Node left, Node right) implements Comparable<Node> {
        public Node(int value) {
            this(value, null, null);
        }

        @Override
        public int compareTo(Node o) {
            return this.value - o.value;
        }

        public void preOrder(Node x, StringBuilder sb) {
            sb.append(x.value).append("\n");
            if (x.left != null) preOrder(x.left, sb);
            if (x.right != null) preOrder(x.right, sb);
        }

        @Override
        public String toString() {
            var sb = new StringBuilder();
            preOrder(this, sb);
            return sb.toString();
        }
    }
}
