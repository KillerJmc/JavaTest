package com.test.algorithm.sort;

import java.util.Arrays;

public class SortUtils {
    public static boolean debug = false;

    /**
     * 如果大于就交换（时间复杂度：2, 即O(1))
     * @param a 数组名
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     * @param <T> 可排序元素
     */
    public static <T extends Comparable<T>> void ifGreaterThanSwap(T[] a, int idx1, int idx2) {
        if (greater(a[idx1], a[idx2])) swap(a, idx1, idx2);
    }

    /**
     * 如果小于就交换（时间复杂度：2, 即O(1))
     * @param a 数组名
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     * @param <T> 可排序元素
     */
    public static <T extends Comparable<T>> void ifLessThanSwap(T[] a, int idx1, int idx2) {
        if (less(a[idx1], a[idx2])) swap(a, idx1, idx2);
    }

    /**
     * 比较是否大于
     * @param o1 第一个元素
     * @param o2 第二个元素
     * @param <O> 可排序元素
     * @return 第一个元素是否大于第二个元素
     */
    public static <O extends Comparable<O>> boolean greater(O o1, O o2) {
        return o1.compareTo(o2) > 0;
    }

    /**
     * 比较是否小于
     * @param o1 第一个元素
     * @param o2 第二个元素
     * @param <O> 可排序元素
     * @return 第一个元素是否小于第二个元素
     */
    public static <O extends Comparable<O>> boolean less(O o1, O o2) {
        return o1.compareTo(o2) < 0;
    }

    /**
     * 交换元素
     * @param a 数组名
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     * @param <T> 可排序元素
     */
    public static <T extends Comparable<T>> void swap(T[] a, int idx1, int idx2) {
        T tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
        if (debug) {
            System.out.println("swap(" + a[idx2] + ", " + a[idx1] + ")");
            System.out.println("result: " + Arrays.toString(a));
        }
    }
}
