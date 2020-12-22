package com.test.algorithm.sort;

import com.jmc.array.Rand;

import static com.test.algorithm.utils.ArrayUtils.*;

public class Sort {
    /**
     * 对基本数据类型数组排序
     * @param a 基本数据类型数组
     * @param m 调用的排序方法
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void basicDataTypeSort(Object a, InvokeSortMethod m) {
        switch (a.getClass().getSimpleName()) {
            case "byte[]", "char[]", "short[]", "int[]", "long[]", "float[]", "double[]" -> {
                var wrappedA = new Comparable[len(a)];
                for (int i = 0; i < len(a); i++) wrappedA[i] = (Comparable) get(a, i);
                m.sort(wrappedA);
                for (int i = 0; i < len(a); i++) set(a, i, wrappedA[i]);
            }

            default -> throw new IllegalArgumentException("输入的不是基本类型数组！");
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
    public static <T> void reverse(T[] a) {
        for (int i = 0; i < a.length / 2; i++) {
            T t = a[i];
            a[i] = a[a.length - 1 - i];
            a[a.length - 1 - i] = t;
        }
    }

    /**
     * 反转数组（时间复杂度：[n/2], 即O(n))
     * @param a 整型数组
     */
    public static void reverse(int[] a) {
        for (int i = 0; i < a.length / 2; i++)
            swap(a, i, a.length - 1 - i);
    }

    /**
     * 冒泡排序（时间复杂度：(n^2 - n) / 2 ~ n^2 - n，即O(n^2) ~ O(n^2))
     * 排序稳定
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void bubbleSort(T[] a) {
        for (int i = a.length - 1; i > 0; i--)
            for (int k = 0; k < i; k++)
                ifGreaterThanSwap(a, k, k + 1);
    }

    /**
     * 冒泡排序（时间复杂度：(n^2 - n) / 2 ~ n^2 - n，即O(n^2) ~ O(n^2))
     * 排序稳定
     * @param a 整型数组
     */
    public static void bubbleSort(int[] a) {
        for (int i = a.length - 1; i > 0; i--)
            for (int k = 0; k < i; k++)
                if (a[k] > a[k + 1])
                    swap(a, k, k + 1);
    }

    /**
     * 选择排序（时间复杂度：n^2/2 + n/2 - 1, 即O(n^2)不变)
     * 排序不稳定
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
     * 选择排序（时间复杂度：n^2/2 + n/2 - 1, 即O(n^2)不变)
     * 排序不稳定
     * @param a 整型数组
     */
    public static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int minIdx = i;
            for (int k = i + 1; k < a.length; k++)
                if (a[k] < a[minIdx])
                    minIdx = k;
            swap(a, i, minIdx);
        }
    }

    /**
     * 插入排序（时间复杂度：n - 1 ~ n^2 - n, 即O(n) ~ O(n^2))
     * 排序稳定
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
     * 插入排序（时间复杂度：n - 1 ~ n^2 - n, 即O(n) ~ O(n^2))
     * 排序稳定
     * @param a 整型数组
     */
    public static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++)
            for (int k = i; k > 0; k--)
                if (a[k] < a[k - 1])
                    swap(a, k, k - 1);
                else
                    break;
    }

    /**
     * 希尔排序（时间复杂度：O(n^(1.3)) ~ O(n^2)，是插入排序的加强版，比插入排序，选择排序，冒泡排序效率高得多
     * 排序不稳定
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void shellSort(T[] a) {
        for (int h = a.length / 2; h >= 1; h /= 2)
            for (int i = h; i < a.length; i++)
                for (int k = i; k - h >= 0; k -= h)
                    if (less(a[k], a[k - h]))
                        swap(a, k, k - h);
                    else
                        break;
    }

    /**
     * 希尔排序（时间复杂度：O(n^(1.3)) ~ O(n^2)，是插入排序的加强版，比插入排序，选择排序，冒泡排序效率高得多
     * 排序不稳定
     * @param a 数组
     */
    public static void shellSort(int[] a) {
        for (int h = a.length / 2; h >= 1; h /= 2)
            for (int i = h; i < a.length; i++)
                for (int k = i; k - h >= 0; k -= h)
                    if (a[k] < a[k - h])
                        swap(a, k, k - h);
                    else
                        break;
    }

    /**
     * 归并排序主类
     * 注：申请assist数组是空间损耗的根本来源
     * @param <T> 数组元素必须是可排序元素
     */
    private static class Merge<T extends Comparable<T>> {
        private T[] assist;

        @SuppressWarnings("unchecked")
        public void sort(T[] a) {
            assist = (T[]) new Comparable[a.length];
            sort(a, 0, a.length - 1);
        }

        private void sort(T[] a, int lo, int hi) {
            if (hi <= lo) return;
            int mid = (hi + lo) / 2;
            sort(a, lo, mid);
            sort(a, mid + 1, hi);
            merge(a, lo, mid, hi);
        }

        private void merge(T[] a, int lo, int mid, int hi) {
            int i = lo, p1 = lo, p2 = mid + 1;
            while (p1 <= mid && p2 <= hi) assist[i++] = less(a[p1], a[p2]) ? a[p1++] : a[p2++];
            while (p1 <= mid) assist[i++] = a[p1++];
            while (p2 <= hi) assist[i++] = a[p2++];
            System.arraycopy(assist, lo, a, lo, hi - lo + 1);
        }
    }

    /**
     * 整型归并排序主类
     * 注：申请assist数组是空间损耗的根本来源
     */
    private static class IntMerge {
        private int[] assist;

        public void sort(int[] a) {
            assist = new int[a.length];
            sort(a, 0, a.length - 1);
        }

        private void sort(int[] a, int lo, int hi) {
            if (hi <= lo) return;
            int mid = (hi + lo) / 2;
            sort(a, lo, mid);
            sort(a, mid + 1, hi);
            merge(a, lo, mid, hi);
        }

        private void merge(int[] a, int lo, int mid, int hi) {
            int i = lo, p1 = lo, p2 = mid + 1;
            while (p1 <= mid && p2 <= hi) assist[i++] = a[p1] < a[p2] ? a[p1++] : a[p2++];
            while (p1 <= mid) assist[i++] = a[p1++];
            while (p2 <= hi) assist[i++] = a[p2++];
            System.arraycopy(assist, lo, a, lo, hi - lo + 1);
        }
    }

    /**
     * 归并排序（时间复杂度：nlog(2, n), 即O(nlogn)不变），排序速度快，是以空间换时间为代价
     * 注：当 n -> ∞ 时，x^1.1 ~ nlog(2, n)，且此时前者比后者更优
     * 排序稳定
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void mergeSort(T[] a) {
        new Merge<T>().sort(a);
    }

    /**
     * 归并排序（时间复杂度：nlog(2, n), 即O(nlogn)不变），排序速度快，是以空间换时间为代价
     * 注：当 n -> ∞ 时，x^1.1 ~ nlog(2, n)，且此时前者比后者更优
     * 排序稳定
     * @param a 整型数组
     */
    public static void mergeSort(int[] a) {
        new IntMerge().sort(a);
    }

    /**
     * 快速排序主类
     * @param <T> 数组元素必须是可排序元素
     */
    private static class Quick<T extends Comparable<T>> {
        public void sort(T[] a) {
            sort(a, 0, a.length - 1);
        }

        private void sort(T[] a, int lo, int hi) {
            if (hi <= lo) return;
            int partition = partition(a, lo, hi);
            sort(a, lo, partition - 1);
            sort(a, partition + 1, hi);
        }

        @SuppressWarnings("all")
        private int partition(T[] a, int lo, int hi) {
            // 优化时间复杂度，尽量避免最坏时间复杂度发生
            swap(a, lo, Rand.nextInt(lo, hi));

            int left = lo, right = hi + 1;

            while (true) {
                while (left < right && !less(a[--right], a[lo]));
                while (left < right && !greater(a[++left], a[lo]));

                if (left == right) {
                    swap(a, lo, right);
                    return right;
                } else {
                    swap(a, left, right);
                }
            }
        }
    }

    /**
     * 整型快速排序主类
     */
    private static class IntQuick {
        public void sort(int[] a) {
            sort(a, 0, a.length - 1);
        }

        private void sort(int[] a, int lo, int hi) {
            if (hi <= lo) return;
            int partition = partition(a, lo, hi);
            sort(a, lo, partition - 1);
            sort(a, partition + 1, hi);
        }

        @SuppressWarnings("all")
        private int partition(int[] a, int lo, int hi) {
            // 优化时间复杂度，尽量避免最坏时间复杂度发生
            swap(a, lo, Rand.nextInt(lo, hi));

            int left = lo, right = hi + 1;

            while (true) {
                while (left < right && !(a[--right] < a[lo]));
                while (left < right && !(a[++left] < a[lo]));

                if (left == right) {
                    swap(a, lo, right);
                    return right;
                } else {
                    swap(a, left, right);
                }
            }
        }
    }

    /**
     * 快速排序（最优/平均时间复杂度：nlog2n, 即O(nlogn)，最坏时间复杂度：n^2, 即O(n^2)）
     * 实践中对数组而言在n较大时快速排序总是优于归并排序，而在链表操作中归并排序更快
     * 理由：对数组而言，归并排序要在两个在内存中相隔较远的两个数组进行操作，对计算机内存访问而言，速度较慢，
     * 而快速排序则只需就近操作，速度较快
     *
     * 排序不稳定
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void quickSort(T[] a) {
        new Quick<T>().sort(a);
    }

    /**
     * 快速排序（最优/平均时间复杂度：nlog2n, 即O(nlogn)，最坏时间复杂度：n^2, 即O(n^2)）
     * 实践中对数组而言在n较大时快速排序总是优于归并排序，而在链表操作中归并排序更快
     * 理由：对数组而言，归并排序要在两个在内存中相隔较远的两个数组进行操作，对计算机内存访问而言，速度较慢，
     * 而快速排序则只需就近操作，速度较快
     *
     * 排序不稳定
     * @param a 整型数组
     */
    public static void quickSort(int[] a) {
        new IntQuick().sort(a);
    }

    /**
     * 堆排序（最优，最坏时间复杂度均为O(nlogn)）
     * @param a 数组
     * @param <T> 数组元素必须是可排序元素
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> void heapSort(T[] a) {
        // 构造堆
        T[] heap = (T[]) new Comparable[a.length + 1];
        System.arraycopy(a, 0, heap, 1, a.length);
        for (int i = heap.length / 2; i >= 1; i--) sink(heap, i, heap.length - 1);

        int N = heap.length - 1;
        while (N != 1) {
            swap(heap, 1, N);
            sink(heap, 1, --N);
        }
        System.arraycopy(heap, 1, a, 0, a.length);
    }

    /**
     * 堆排序：下沉
     * @param heap 堆数组
     * @param x 需要下沉元素对应的下标
     * @param range 下沉范围
     * @param <T> 数组元素必须是可排序元素
     */
    private static <T extends Comparable<T>> void sink(T[] heap, int x, int range) {
        while (x * 2 <= range) {
            int maxIdx = x * 2 + 1 > range ? x * 2 : greaterIdx(heap, x * 2, x * 2 + 1);
            if (less(heap[x], heap[maxIdx]))
                swap(heap, x, x = maxIdx);
            else
                return;
        }
    }

    /**
     * 堆排序
     * @param a 整型数组
     */
    public static void heapSort(int[] a) {
        // 构造堆
        int[] heap = new int[a.length + 1];
        System.arraycopy(a, 0, heap, 1, a.length);
        for (int i = heap.length / 2; i >= 1; i--) sink(heap, i, heap.length - 1);

        int N = heap.length - 1;
        while (N != 1) {
            swap(heap, 1, N);
            sink(heap, 1, --N);
        }
        System.arraycopy(heap, 1, a, 0, a.length);
    }

    /**
     * 堆排序：下沉
     * @param heap 整型堆数组
     * @param x 需要下沉元素对应的下标
     * @param range 下沉范围
     */
    private static void sink(int[] heap, int x, int range) {
        while (x * 2 <= range) {
            int maxIdx = x * 2 + 1 > range ? x * 2 : heap[x * 2] > heap[x * 2 + 1] ? x * 2 : x * 2 + 1;
            if (heap[x] < heap[maxIdx])
                swap(heap, x, x = maxIdx);
            else
                return;
        }
    }
}
