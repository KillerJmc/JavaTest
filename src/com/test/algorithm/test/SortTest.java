package com.test.algorithm.test;

import com.jmc.lang.Timers;
import com.test.algorithm.sort.Sort;

import java.util.Arrays;
import java.util.stream.IntStream;

import static com.test.algorithm.sort.Sort.*;

public class SortTest {
    public static void main(String[] args) {
//        debug = true;
        int[] a = {3, 1, 4, 5, 2};
        System.out.println(Arrays.toString(a));
        a = sort(a, Sort::shellSort);
        System.out.println(Arrays.toString(a));
    }

    public static void sortTimer(int n, String name, RunnableTestArr r) {
        Integer[] a = IntStream.rangeClosed(1, n).boxed().toArray(Integer[]::new);
        Sort.reverse(a);
        Timers.milliTimer(() -> r.test(a), name);
    }

    public interface RunnableTestArr {
        <T extends Comparable<T>> void test(T[] a);
    }

}
