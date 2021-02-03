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
        objectSortTimer(pow, "BubbleSort", Sort::bubbleSort);
        objectSortTimer(pow, "SelectionSort", Sort::selectionSort);
        objectSortTimer(pow, "InsertionSort", Sort::insertionSort);
        objectSortTimer(pow, "BinaryInsertionSort", Sort::binaryInsertionSort);
        System.out.println("-----------------------------------");
    }

    public static void testObject2(int pow) {
        System.out.println("testObject2, pow = " + pow);
        objectSortTimer(pow, "HeapSort", Sort::heapSort);
        objectSortTimer(pow, "ShellSort", Sort::shellSort);
        objectSortTimer(pow, "MergeSort", Sort::mergeSort);
        objectSortTimer(pow, "QuickSort", Sort::quickSort);
        objectSortTimer(pow, "JavaSort", Arrays::sort);
        System.out.println("-----------------------------------");
    }

    public static void testInt1(int pow) {
        System.out.println("testInt1, pow = " + pow);
        intSortTimer(pow, "BubbleSort", Sort::bubbleSort);
        intSortTimer(pow, "SelectionSort", Sort::selectionSort);
        intSortTimer(pow, "InsertionSort", Sort::insertionSort);
        intSortTimer(pow, "BinaryInsertionSort", Sort::binaryInsertionSort);
        System.out.println("-----------------------------------");
    }

    public static void testInt2(int pow) {
        System.out.println("testInt2, pow = " + pow);
        intSortTimer(pow, "HeapSort", Sort::heapSort);
        intSortTimer(pow, "ShellSort", Sort::shellSort);
        intSortTimer(pow, "MergeSort", Sort::mergeSort);
        intSortTimer(pow, "QuickSort", Sort::quickSort);
        intSortTimer(pow, "BucketSort", Sort::bucketSort);
        intSortTimer(pow, "CountingSort", Sort::countingSort);
        intSortTimer(pow, "RadixSort", Sort::radixSort);
        intSortTimer(pow, "AdvancedRadixSort", Sort::advancedRadixSort);
        intSortTimer(pow, "JavaSort", Arrays::sort);
        System.out.println("-----------------------------------");
    }

    public static void objectSortTimer(int pow, String name, Sort.InvokeSortMethod m) {
        if (pow > 8) throw new Error();
        int n = (int) Math.pow(10, pow);
        var a = IntStream.rangeClosed(1, n).boxed().toArray(Integer[]::new);
        reverse(a);
        Timers.milliTimer(() -> m.sort(a), "Object -> " + name);
    }

    public static void intSortTimer(int pow, String name, InvokeIntMethod m) {
        if (pow > 8) throw new Error();
        int n = (int) Math.pow(10, pow);
        var a = IntStream.rangeClosed(1, n).toArray();
        reverse(a);
        Timers.milliTimer(() -> m.sort(a), "Int -> " + name);
    }

    public interface InvokeIntMethod {
        void sort(int[] a);
    }
}