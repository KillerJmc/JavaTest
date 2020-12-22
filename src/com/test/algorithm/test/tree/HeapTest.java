package com.test.algorithm.test.tree;

import com.test.algorithm.sort.Sort;
import com.test.algorithm.tree.impl.*;
import org.junit.Test;

import java.util.Arrays;

public class HeapTest {
    @Test
    public void test() {
        var heap = new Heap<String>(7);
        heap.insert("A");
        heap.insert("B");
        heap.insert("C");
        heap.insert("D");
        heap.insert("E");
        heap.insert("F");
        heap.insert("G");

        String result;
        while ((result = heap.delMax()) != null) {
            System.out.print(result + " ");
        }
    }

    @Test
    public void test2() {
        var a = new Character[] {'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E', 'S'};
        Sort.heapSort(a);
        System.out.println(Arrays.toString(a));
    }

    @Test
    public void test3() {
        var queue = new MaxPriorityQueue<String>(7);

        queue.insert("A");
        queue.insert("B");
        queue.insert("C");
        queue.insert("D");
        queue.insert("E");
        queue.insert("F");
        queue.insert("G");

        while (!queue.isEmpty()) {
            System.out.println(queue.delMax());
        }
    }

    @Test
    public void test4() {
        var queue = new MinPriorityQueue<String>(7);

        queue.insert("G");
        queue.insert("F");
        queue.insert("E");
        queue.insert("D");
        queue.insert("C");
        queue.insert("B");
        queue.insert("A");

        while (!queue.isEmpty()) {
            System.out.println(queue.delMin());
        }
    }

    @Test
    public void test5() {
        var queue = new IndexMaxPriorityQueue<String>(4);
        queue.insert(0, "A");
        queue.insert(1, "B");
        queue.insert(2, "C");
        queue.insert(3, "D");

        System.out.println("size = " + queue.size());
        System.out.println("maxIdx = " + queue.maxIdx());

//        queue.insert(0, "Z");
        System.out.println(queue.contains(0));
        queue.delete(0);
        System.out.println(queue.contains(0));

        queue.delete(3);

        // 0 1
        while (!queue.isEmpty()) {
            int idx = queue.delMax();
            System.out.println(idx);
        }
    }

    @Test
    public void test6() {
        var queue = new IndexMinPriorityQueue<String>(4);
        queue.insert(0, "D");
        queue.insert(1, "C");
        queue.insert(2, "B");
        queue.insert(3, "A");

        System.out.println("size = " + queue.size());
        System.out.println("minIdx = " + queue.minIdx());

//        queue.insert(0, "Z");
        System.out.println(queue.contains(0));
        queue.delete(0);
        System.out.println(queue.contains(0));

        queue.delete(3);

        // 0 1
        while (!queue.isEmpty()) {
            int idx = queue.delMin();
            System.out.println(idx);
        }
    }
}
