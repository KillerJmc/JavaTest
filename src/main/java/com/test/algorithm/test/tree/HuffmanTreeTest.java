package com.test.algorithm.test.tree;

import com.test.algorithm.tree.impl.HuffmanTree;
import org.junit.Test;

public class HuffmanTreeTest {
    @Test
    public void test() {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        System.out.println(HuffmanTree.create(arr));
    }
}
