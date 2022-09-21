package com.test.algorithm.test.tree;

import com.test.algorithm.tree.impl.RedBlackTree;
import org.junit.Test;

public class RedBlackTreeTest {
    @Test
    public void test() {
        var tree = new RedBlackTree<Integer, String>();
        tree.put(1, "Jmc");
        tree.put(2, "Lucy");
        tree.put(3, "Merry");

        System.out.println(tree.get(1));
        System.out.println(tree.get(2));
        System.out.println(tree.get(3));
    }
}
