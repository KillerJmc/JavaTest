package com.test.algorithm.test.tree;

import com.test.algorithm.list.linked.impl.LinkedQueue;
import com.test.algorithm.list.sequence.impl.SequenceQueue;
import com.test.algorithm.tree.impl.BinaryTree;
import org.junit.Test;

import static com.test.main.Tools.newLine;

public class BinaryTreeTest {
    @Test
    public void test() {
        var tree = new BinaryTree<Integer, String>();
        tree.put(2, "李四");
        tree.put(1, "张三");
        tree.put(3, "王五");
        System.out.println(tree.size());

        System.out.println(tree.get(2));

        tree.delete(2);
        System.out.println(tree.size());
        System.out.println(tree.get(2));

        newLine();

        tree.delete(3);
        tree.put(1, "666");
        System.out.println("1 -> " + tree.get(1));
        System.out.println("2 -> " + tree.get(2));
        System.out.println("3 -> " + tree.get(3));
        System.out.println(tree.size());
    }

    @Test
    public void test2() {
        var tree = new BinaryTree<Integer, String>();
        tree.put(2, "Lucy");
        tree.put(1, "Jmc");
        tree.put(3, "Jerry");
        System.out.println(tree.max());
        System.out.println(tree.min());

    }

    @Test
    public void test3() {
        var tree = new BinaryTree<Character, Integer>() {{
            put('E', 5);
            put('B', 2);
            put('G', 7);
            put('A', 1);
            put('D', 4);
            put('F', 6);
            put('H', 8);
            put('C', 3);
        }};

        newLine(() -> {
            var keys = tree.preErgodic();
            for (var key : keys) {
                System.out.println(key + " -> " + tree.get(key));
            }
        });

        newLine(() -> {
            var keys = tree.midErgodic();
            for (var key : keys) {
                System.out.println(key + " -> " + tree.get(key));
            }
        });

        newLine(() -> {
            var keys = tree.afterErgodic();
            for (var key : keys) {
                System.out.println(key + " -> " + tree.get(key));
            }
        });

        newLine(() -> {
            var keys = tree.laserErgodic();
            for (var key : keys) {
                System.out.println(key + " -> " + tree.get(key));
            }
        });
    }

    @Test
    public void test4() {
        var tree = new BinaryTree<Character, Integer>() {{
            put('E', 5);
            put('B', 2);
            put('G', 7);
            put('A', 1);
            put('D', 4);
            put('F', 6);
            put('H', 8);
            put('C', 3);
        }};
        System.out.println(tree.maxDepth());
    }

    private static class Node<T> {
        public T item;
        public Node<T> left;
        public Node<T> right;

        public Node(T item) {
            this.item = item;
        }
    }

    private static void midPrint(Node<String> x) {
        if (x == null) return;

        if (x.left != null) midPrint(x.left);

        System.out.print(x.item + " ");

        if (x.right != null) midPrint(x.right);
    }

    // 解决折纸问题
    public void printFoldResult(int n) {
        // Sn = 2^0 + 2^1 + ... + 2^(n - 1) = 2^n - 1
        int size = (2 << n - 1) - 1 - 1;

        var root = new Node<>("down");
        var queue = new LinkedQueue<Node<String>>() {{ add(root); }};

        while (size != 0) {
            var node = queue.poll();
            queue.add(node.left = new Node<>("down"));
            queue.add(node.right = new Node<>("up"));
            size -= 2;
        }

        midPrint(root);
        System.out.println();
    }

    @Test
    public void test5() {
        for (int i = 1; i <= 4; i++)
            printFoldResult(i);
    }
}
