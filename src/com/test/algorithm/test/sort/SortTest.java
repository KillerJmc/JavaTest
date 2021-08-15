package com.test.algorithm.test.sort;

import com.jmc.array.Array;
import com.jmc.lang.timer.Timers;
import com.jmc.reference.Func;
import com.test.algorithm.sort.Sort;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SortTest {
    public static void main(String[] args) {
        testInt1(5);
        testInt2(8);
    }

    public static void testInt1(int pow) {
        System.out.println("testInt1, pow = " + pow);
        intSortTimer1(pow, "BubbleSort", Sort::bubbleSort);
        intSortTimer1(pow, "SelectionSort", Sort::selectionSort);
        intSortTimer1(pow, "InsertionSort", Sort::insertionSort);
        intSortTimer1(pow, "BinaryInsertionSort", Sort::binaryInsertionSort);
        System.out.println("-----------------------------------");
    }

    public static void testInt2(int pow) {
        System.out.println("testInt2, pow = " + pow);
        intSortTimer1(pow, "HeapSort", Sort::heapSort);
        intSortTimer1(pow, "ShellSort", Sort::shellSort);
        intSortTimer1(pow, "MergeSort", Sort::mergeSort);
        intSortTimer1(pow, "QuickSort", Sort::quickSort);
        intSortTimer(pow, "BucketSort", Sort::bucketSort);
        intSortTimer(pow, "CountingSort", Sort::countingSort);
        intSortTimer(pow, "RadixSort", Sort::radixSort);
        intSortTimer(pow, "AdvancedRadixSort", Sort::advancedRadixSort);
        intSortTimer(pow, "JavaSort", Arrays::sort);
        System.out.println("-----------------------------------");
    }

    public static void intSortTimer1(int pow, String name, Func.Void1<Array<Integer>> sortMethod) {
        if (pow > 8) throw new Error();
        int n = (int) Math.pow(10, pow);
        var a = IntStream.rangeClosed(1, n).toArray();
        Sort.reverse(Array.of(a));
        Timers.milliTimer(() -> sortMethod.invoke(Array.of(a)), "Int -> " + name);
    }

    public static void intSortTimer(int pow, String name, Func.Void1<int[]> sortMethod) {
        if (pow > 8) throw new Error();
        int n = (int) Math.pow(10, pow);
        var a = IntStream.rangeClosed(1, n).toArray();
        Sort.reverse(Array.of(a));
        Timers.milliTimer(() -> sortMethod.invoke(a), "Int -> " + name);
    }
}