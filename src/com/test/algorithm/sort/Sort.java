package com.test.algorithm.sort;

import javax.management.RuntimeErrorException;
import java.util.Arrays;

import static com.test.algorithm.sort.SortUtils.*;

public class Sort {
    /**
     * 对基本数据类型数组排序
     * @param basicDataTypeArr 基本数据类型数组
     * @param m 调用的排序方法
     * @param <T> 基本数据类型数组
     * @return 排序后的数组
     */
    public static <T> T sort(T basicDataTypeArr, InvokeSortMethod m) {
        if (basicDataTypeArr instanceof int[] intA) {
            Integer[] a = Arrays.stream(intA).boxed().toArray(Integer[]::new);
            m.sort(a);
            return (T) Arrays.stream(a).mapToInt(t -> t).toArray();
        } else if (basicDataTypeArr instanceof long[] longA) {
            Long[] a = Arrays.stream(longA).boxed().toArray(Long[]::new);
            m.sort(a);
            return (T) Arrays.stream(a).mapToLong(t -> t).toArray();
        } else if (basicDataTypeArr instanceof double[] doubleA) {
            Double[] a = Arrays.stream(doubleA).boxed().toArray(Double[]::new);
            m.sort(a);
            return (T) Arrays.stream(a).mapToDouble(t -> t).toArray();
        } else {
            throw new IllegalArgumentException("不支持的基本类型数组！");
        }
    }

    /**
     * 调用排序方法的接口
     */
    public interface InvokeSortMethod {
        /**
         *
         * @param a 包装类数组
         * @param <T> 数组元素必须是可排序元素
         */
        <T extends Comparable<T>> void sort(T[] a);
    }

    /**
     * 反转数组（时间复杂度：[n/2], 即O(n))
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void reverse(T[] a) {
        for (int i = 0; i < a.length / 2; i++)
            swap(a, i, a.length - 1 - i);
    }

    /**
     * 冒泡排序（时间复杂度：n^2 - n，即O(n^2))
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void bubbleSort(T[] a) {
        for (int i = a.length - 1; i > 0; i--)
            for (int k = 0; k < i; k++)
                ifGreaterThanSwap(a, k, k + 1);
    }

    /**
     * 选择排序（时间复杂度：n^2/2 + n/2 - 1, 即O(n^2))
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void selectionSort(T[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int minIdx = i;
            for (int k = i + 1; k < a.length; k++)
                if (less(a[k], a[minIdx]))
                    minIdx = k;
            swap(a, i, minIdx);
        }
    }

    /**
     * 插入排序（时间复杂度：n^2 - n, 即O(n^2))
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void insertionSort(T[] a) {
        for (int i = 1; i < a.length; i++)
            for (int k = i; k > 0; k--)
                if (less(a[k], a[k - 1]))
                    swap(a, k, k - 1);
                else
                    break;
    }

    /**
     * 希尔排序（时间复杂度：O(n^(1.3~2)))，是插入排序的加强版，比插入排序，选择排序，冒泡排序效率高得多
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void shellSort(T[] a) {
        int h = a.length / 2;
        while (h >= 1) {
            for (int i = h; i < a.length; i++)
                for (int k = i; k >= h; k -= h)
                    if (less(a[k], a[k - h]))
                        swap(a, k, k - h);
                    else
                        break;
            h /= 2;
        }
    }
}
