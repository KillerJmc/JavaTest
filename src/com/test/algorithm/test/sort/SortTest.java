package com.test.algorithm.test.sort;

import com.jmc.lang.timer.Timers;
import com.test.algorithm.sort.Sort;
import com.test.main.Tools;

import java.util.Arrays;
import java.util.stream.IntStream;

import static com.test.algorithm.sort.Sort.reverse;

public class SortTest {
    public static void main(String[] args) {
        Tools.sleep(1000);
        testObject1(5);
        testObject2(8);
        testInt1(5);
        testInt2(8);
    }

    public static void testObject1(int pow) {
        System.out.println("testObject1, pow = " + pow);
        int n = (int) Math.pow(10, pow);
        objectSortTimer(n, "BubbleSort", Sort::bubbleSort);
        objectSortTimer(n, "SelectionSort", Sort::selectionSort);
        objectSortTimer(n, "InsertionSort", Sort::insertionSort);
        System.out.println("-----------------------------------");
    }

    public static void testObject2(int pow) {
        System.out.println("testObject2, pow = " + pow);
        int n = (int) Math.pow(10, pow);
        objectSortTimer(n, "HeapSort", Sort::heapSort);
        objectSortTimer(n, "ShellSort", Sort::shellSort);
        objectSortTimer(n, "MergeSort", Sort::mergeSort);
        objectSortTimer(n, "QuickSort", Sort::quickSort);
        objectSortTimer(n, "JavaSort", Arrays::sort);
        System.out.println("-----------------------------------");
    }

    public static void testInt1(int pow) {
        System.out.println("testInt1, pow = " + pow);
        int n = (int) Math.pow(10, pow);
        intSortTimer(n, "BubbleSort", Sort::bubbleSort);
        intSortTimer(n, "SelectionSort", Sort::selectionSort);
        intSortTimer(n, "InsertionSort", Sort::insertionSort);
        System.out.println("-----------------------------------");
    }

    public static void testInt2(int pow) {
        System.out.println("testInt2, pow = " + pow);
        int n = (int) Math.pow(10, pow);
        intSortTimer(n, "HeapSort", Sort::heapSort);
        intSortTimer(n, "ShellSort", Sort::shellSort);
        intSortTimer(n, "MergeSort", Sort::mergeSort);
        intSortTimer(n, "QuickSort", Sort::quickSort);
        intSortTimer(n, "JavaSort", Arrays::sort);
        System.out.println("-----------------------------------");
    }

    public static void objectSortTimer(int n, String name, Sort.InvokeSortMethod m) {
        if (n > (int) Math.pow(10, 8)) throw new Error();
        var a = IntStream.rangeClosed(1, n).boxed().toArray(Integer[]::new);
        reverse(a);
        Timers.milliTimer(() -> m.sort(a), "Object -> " + name);
    }

    public static void intSortTimer(int n, String name, InvokeIntMethod m) {
        if (n > (int) Math.pow(10, 8)) throw new Error();
        var a = IntStream.rangeClosed(1, n).toArray();
        reverse(a);
        Timers.milliTimer(() -> m.sort(a), "Int -> " + name);
    }

    public interface InvokeIntMethod {
        void sort(int[] a);
    }
}