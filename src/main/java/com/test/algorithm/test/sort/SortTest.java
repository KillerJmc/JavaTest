package com.test.algorithm.test.sort;

import com.jmc.array.Array;
import com.jmc.lang.Threads;
import com.jmc.lang.ref.Func;
import com.jmc.util.Timers;
import com.test.algorithm.sort.Sort;

import java.util.Arrays;
import java.util.stream.IntStream;

import static com.test.algorithm.sort.Sort.reverse;

public class SortTest {
    public static void main(String[] args) {
        Threads.sleep(1000);
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
        objectSortTimer2(pow, "JavaSort", Arrays::sort);
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

    public static void objectSortTimer(int pow, String name, Func.Void1<Array<Integer>> sortMethod) {
        if (pow > 8) throw new Error();
        int n = (int) Math.pow(10, pow);
        var a = IntStream.rangeClosed(1, n).boxed().toArray(Integer[]::new);
        reverse(Array.of(a));
        Timers.milliTimer(() -> sortMethod.invoke(Array.of(a)), "Object -> " + name);
        verify(name, a);
    }

    public static void objectSortTimer2(int pow, String name, Func.Void1<Integer[]> sortMethod) {
        if (pow > 8) throw new Error();
        int n = (int) Math.pow(10, pow);
        var a = IntStream.rangeClosed(1, n).boxed().toArray(Integer[]::new);
        reverse(Array.of(a));
        Timers.milliTimer(() -> sortMethod.invoke(a), "Object -> " + name);
        verify(name, a);
    }

    public static void intSortTimer(int pow, String name, Func.Void1<int[]> sortMethod) {
        if (pow > 8) throw new Error();
        int n = (int) Math.pow(10, pow);
        var a = IntStream.rangeClosed(1, n).toArray();
        reverse(a);
        Timers.milliTimer(() -> sortMethod.invoke(a), "Int -> " + name);
        verify(name, a);
    }

    public static void verify(String name, int[] res) {
        var sorted = res.clone();
        Arrays.sort(sorted);

        if (!Arrays.equals(res, sorted)) {
            throw new RuntimeException(name + " failed!");
        }
    }

    public static void verify(String name, Integer[] res) {
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < expected.length; i++) {
            if (res[i] != expected[i]) {
                throw new RuntimeException(name + " failed!");
            }
        }
    }
}
