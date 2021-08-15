package com.test.algorithm.sort;

import com.jmc.array.Array;
import com.jmc.array.Arrs;
import com.jmc.lang.extend.Rand;
import com.test.algorithm.list.sequence.impl.DoubleArray;
import com.test.algorithm.list.sequence.impl.IntArray;

import java.util.Arrays;

import static com.jmc.util.Compare.gt;
import static com.jmc.util.Compare.lt;

@SuppressWarnings("unused")
public class Sort {
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
     * <p>二分插入排序（最好时间复杂度：2 * log2 n（每次找到最右边的数，无交换）, 其中log2 n! ~ 30n(n->∞)，即O(n)
     * 最坏时间复杂度：(n^2 - n) / 2 + 2 * log2 n!,
     * 因此原式 ~ n^2 / 2 + 59.5 n , 即O(n^2)）
     *
     * <p>排序稳定
     *
     * <p>这相比直接插入排序，没有减少元素交换次数，但是大大减少了比较的次数，因此性能提升明显，成为O(n^2)排序中最快的排序算法
     *
     * @param a 整型数组
     */
    public static void binaryInsertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int lo = 0, hi = i - 1, mid, k = a[i];
            while (hi >= lo) {
                mid = (lo + hi) / 2;
                if (a[mid] >= k) {
                    if (mid == 0 || a[mid - 1] < k) {
                        System.arraycopy(a, mid, a, mid + 1, i - mid);
                        a[mid] = k;
                        break;
                    } else {
                        hi = mid - 1;
                    }
                } else {
                    lo = mid + 1;
                }
            }
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
     * @param a 整型数组
     */
    public static void mergeSort(int[] a) {
        new IntMerge().sort(a);
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
                while (left < right && !(a[++left] > a[lo]));

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
     * @param a 整型数组
     */
    public static void quickSort(int[] a) {
        new IntQuick().sort(a);
    }

    /**
     * 堆排序（最优，最坏时间复杂度均为O(nlogn)）
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

    /**
     * 桶排序 （时间复杂度O(n)，空间复杂度较高，是以空间换时间，仅适合对数据较均匀的整数，浮点数数组排序）
     * @param a 整型数组
     */
    public static void bucketSort(int[] a) {
        int max = a[0], min = max;
        for (var t : a) {
            if (t > max) max = t;
            if (t < min) min = t;
        }

        int eachRange = max - min < 100000 ? 1 :
            max - min < 1000000 ? 10 :
            max - min < 10000000 ? 100 : 1000;
        int bucketCount = (max - min) / eachRange + 1;

        IntArray[] buckets = new IntArray[bucketCount];
        for (int i = 0; i < buckets.length; i++) buckets[i] = new IntArray();

        for (var t : a) {
            int bucketIdx = (t - min) / eachRange;
            buckets[bucketIdx].insert(t);
        }

        int p = 0;
        for (var tmp : buckets) {
            if (tmp.size() == 0) continue;
            var bucket = tmp.getArray();
            if (bucket.length > 1) bucketSort(bucket);
            for (var t : bucket) a[p++] = t;
        }
    }

    /**
     * 桶排序（时间复杂度O(n)，空间复杂度较高，是以空间换时间，仅适合对数据较均匀的整数，浮点数数组排序）
     * 此次递归实现排序是稳定的，正常情况下桶排序是否稳定取决与对每个桶的排序算法的稳定性
     * @param a 浮点型数组
     */
    public static void bucketSort(double[] a) {
        double max = a[0], min = max;
        for (var t : a) {
            if (t > max) max = t;
            if (t < min) min = t;
        }

        int eachRange = max - min < 100000 ? 1 :
            max - min < 1000000 ? 10 :
            max - min < 10000000 ? 100 : 1000;
        int bucketCount = (int) ((max - min) / eachRange + 1);

        DoubleArray[] buckets = new DoubleArray[bucketCount];
        for (int i = 0; i < buckets.length; i++) buckets[i] = new DoubleArray();

        for (var t : a) {
            int bucketIdx = (int) ((t - min) / eachRange);
            buckets[bucketIdx].insert(t);
        }

        int p = 0;
        for (var tmp : buckets) {
            if (tmp.size() == 0) continue;
            var bucket = tmp.getArray();
            if (bucket.length > 1) bucketSort(bucket);
            for (var t : bucket) a[p++] = t;
        }
    }

    /**
     * 计数排序（时间复杂度O(n)，空间复杂度较高，是桶排序的延伸，仅适合对范围较小的整数数组排序）
     * 经过优化，排序稳定
     * @param a 整型数组
     */
    public static void countingSort(int[] a) {
        int max = a[0], min = max;
        for (var t : a) {
            if (t > max) max = t;
            if (t < min) min = t;
        }

        int[] count = new int[max - min + 1];
        for (var t : a) count[t - min]++;

        // 保证稳定性
        // count数组表示对应元素（多个）在原数组中的最后一个这种元素的位置
        for (int i = 1; i < count.length; i++) count[i] += count[i - 1];

        int[] tmp = Arrays.copyOf(a, a.length);
        for (int i = tmp.length - 1; i >= 0; i--) a[count[tmp[i] - min]-- - 1] = tmp[i];
    }

    /**
     * 基数排序（时间复杂度O(n)，是计数排序的延伸，省空间，仅适合对范围较小的正整数数组排序）
     * 经优化后排序稳定
     * @param a 正整型数组
     */
    public static void radixSort(int[] a) {
        int max = a[0];
        for (var t : a) if (t > max) max = t;

        int maxDigits = 0;
        while (max != 0) {
            max /= 10;
            maxDigits++;
        }

        int digit = 1;
        int[] count = new int[10];

        while (maxDigits-- > 0) {
            for (var t : a) count[t / digit % 10]++;
            for (int i = 1; i < count.length; i++) count[i] += count[i - 1];

            int[] tmp = Arrays.copyOf(a, a.length);
            for (int i = tmp.length - 1; i >= 0; i--) a[count[tmp[i] / digit % 10]-- - 1] = tmp[i];

            digit *= 10;
            Arrays.fill(count, 0);
        }
    }

    /**
     * 优化的基数排序（时间复杂度O(n)，省空间，适用于于范围大但长度不长的正整数排序）
     * 排序稳定
     * @param a 正整型数组
     */
    public static void advancedRadixSort(int[] a) {
        int radix = 1 << 10;
        int[] count, pow;

        int max = a[0];
        for (var t : a) if (t > max) max = t;

        if (max <= 1 << 10) {       count = new int[radix]; pow = new int[] {0}; }
        else if (max <= 1 << 20) {  count = new int[radix]; pow = new int[] {0, 10}; }
        else if (max <= 1 << 30) {  count = new int[radix]; pow = new int[] {0, 10, 20}; }
        else {  radix = 1 << 11;    count = new int[radix]; pow = new int[] {0, 10, 20}; }

        for (int k : pow) {
            for (var t : a) count[t >> k & (radix - 1)]++;
            for (int i = 1; i < count.length; i++) count[i] += count[i - 1];

            int[] tmp = Arrays.copyOf(a, a.length);
            for (int i = tmp.length - 1; i >= 0; i--) a[count[tmp[i] >> k & (radix - 1)]-- - 1] = tmp[i];

            Arrays.fill(count, 0);
        }
    }

    private static void swap(int[] a, int idx1, int idx2) {
        int tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }

    /**
     * 反转数组（时间复杂度：[n/2], 即O(n))
     * @param a 通用数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void reverse(Array<T> a) {
        for (int i = 0; i < a.len() / 2; i++) {
            Arrs.swap(a, i, a.len() - 1 - i);
        }
    }

    /**
     * 冒泡排序（时间复杂度：(n^2 - n) / 2 ~ n^2 - n，即O(n^2) ~ O(n^2))
     * 排序稳定
     * @param a 通用数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void bubbleSort(Array<T> a) {
        for (int i = a.len() - 1; i > 0; i--)
            for (int k = 0; k < i; k++)
                if (gt(a, k, k + 1))
                    Arrs.swap(a, k, k + 1);
    }

    /**
     * 选择排序（时间复杂度：n^2/2 + n/2 - 1, 即O(n^2)不变)
     * 排序不稳定
     * @param a 通用数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void selectionSort(Array<T> a) {
        for (int i = 0; i < a.len() - 1; i++) {
            int minIdx = i;
            for (int k = i + 1; k < a.len(); k++)
                if (lt(a, k, minIdx))
                    minIdx = k;
            Arrs.swap(a, i, minIdx);
        }
    }

    /**
     * 插入排序（时间复杂度：n - 1 ~ n^2 - n, 即O(n) ~ O(n^2))
     * 排序稳定
     * @param a 通用数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void insertionSort(Array<T> a) {
        for (int i = 1; i < a.len(); i++)
            for (int k = i; k > 0; k--)
                if (lt(a, k, k - 1))
                    Arrs.swap(a, k, k - 1);
                else
                    break;
    }

    /**
     * <p>二分插入排序（最好时间复杂度：2 * log2 n（每次找到最右边的数，无交换）, 其中log2 n! ~ 30n(n->∞)，即O(n)
     * 最坏时间复杂度：(n^2 - n) / 2 + 2 * log2 n!,
     * 因此原式 ~ n^2 / 2 + 59.5 n , 即O(n^2)）
     *
     * <p>排序稳定
     *
     * <p>这相比直接插入排序，没有减少元素交换次数，但是大大减少了比较的次数，因此性能提升明显，成为O(n^2)排序中最快的排序算法
     *
     * @param a 通用数组
     * @param <T> 数组元素必须是可排序元素
     */
    @SuppressWarnings("all")
    public static <T extends Comparable<T>> void binaryInsertionSort(Array<T> a) {
        for (int i = 1; i < a.len(); i++) {
            int lo = 0, hi = i - 1, mid;
            while (hi >= lo) {
                mid = (lo + hi) / 2;
                if (gt(a, mid, i)) {
                    if (mid == 0 || lt(a, mid - 1, i)) {
                        T k = a.get(i);
                        System.arraycopy(a.toArray(), mid, a.toArray(), mid + 1, i - mid);
                        a.set(mid, k);
                        break;
                    } else {
                        hi = mid - 1;
                    }
                } else {
                    lo = mid + 1;
                }
            }
        }
    }

    /**
     * 希尔排序（时间复杂度：O(n^(1.3)) ~ O(n^2)，是插入排序的加强版，比插入排序，选择排序，冒泡排序效率高得多
     * 排序不稳定
     * @param a 通用数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void shellSort(Array<T> a) {
        for (int h = a.len() / 2; h >= 1; h /= 2)
            for (int i = h; i < a.len(); i++)
                for (int k = i; k - h >= 0; k -= h)
                    if (lt(a, k, k - h))
                        Arrs.swap(a, k, k - h);
                    else
                        break;
    }

    /**
     * 归并排序主类
     * 注：申请assist数组是空间损耗的根本来源
     * @param <T> 数组元素必须是可排序元素
     */
    private static class Merge<T extends Comparable<T>> {
        private Array<Comparable<?>> assist;

        @SuppressWarnings("unchecked")
        public void sort(Array<T> a) {
            assist = Array.of(new Comparable[a.len()]);
            sort(a, 0, a.len() - 1);
        }

        private void sort(Array<T> a, int lo, int hi) {
            if (hi <= lo) return;
            int mid = (hi + lo) / 2;
            sort(a, lo, mid);
            sort(a, mid + 1, hi);
            merge(a, lo, mid, hi);
        }

        @SuppressWarnings("all")
        private void merge(Array<T> a, int lo, int mid, int hi) {
            int i = lo, p1 = lo, p2 = mid + 1;
            while (p1 <= mid && p2 <= hi) assist.set(i++, lt(a, p1, p2) ? a.get(p1++) : a.get(p2++));
            while (p1 <= mid) assist.set(i++, a.get(p1++));
            while (p2 <= hi) assist.set(i++, a.get(p2++));
            for (int j = lo; j <= hi; j++) {
                a.set(j, (T) assist.get(j));
            }
        }
    }

    /**
     * 归并排序（时间复杂度：nlog(2, n), 即O(nlogn)不变），排序速度快，是以空间换时间为代价
     * 注：当 n -> ∞ 时，x^1.1 ~ nlog(2, n)，且此时前者比后者更优
     * 排序稳定
     * @param a 通用数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void mergeSort(Array<T> a) {
        new Merge<T>().sort(a);
    }

    /**
     * 快速排序主类
     * @param <T> 数组元素必须是可排序元素
     */
    private static class Quick<T extends Comparable<T>> {
        public void sort(Array<T> a) {
            sort(a, 0, a.len() - 1);
        }

        private void sort(Array<T> a, int lo, int hi) {
            if (hi <= lo) return;
            int partition = partition(a, lo, hi);
            sort(a, lo, partition - 1);
            sort(a, partition + 1, hi);
        }

        @SuppressWarnings("all")
        private int partition(Array<T> a, int lo, int hi) {
            // 优化时间复杂度，尽量避免最坏时间复杂度发生
            Arrs.swap(a, lo, Rand.nextInt(lo, hi));

            int left = lo, right = hi + 1;

            while (true) {
                while (left < right && !lt(a, --right, lo));
                while (left < right && !gt(a, ++left, lo));

                if (left == right) {
                    Arrs.swap(a, lo, right);
                    return right;
                } else {
                    Arrs.swap(a, left, right);
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
     * @param a 通用数组
     * @param <T> 数组元素必须是可排序元素
     */
    public static <T extends Comparable<T>> void quickSort(Array<T> a) {
        new Quick<T>().sort(a);
    }

    /**
     * 堆排序（最优，最坏时间复杂度均为O(nlogn)）
     * @param a 通用数组
     * @param <T> 数组元素必须是可排序元素
     */
    @SuppressWarnings("all")
    public static <T extends Comparable<T>> void heapSort(Array<T> a) {
        // 构造堆
        var heap = Array.of(new Comparable[a.len() + 1]);
        for (int i = 0; i < a.len(); i++) {
            heap.set(i + 1, a.get(i));
        }
        // 只沉降非叶子节点
        for (int i = heap.len() / 2; i >= 1; i--) sink(heap, i, heap.len() - 1);

        int N = heap.len() - 1;
        while (N != 1) {
            Arrs.swap(heap, 1, N);
            sink(heap, 1, --N);
        }
        for (int i = 0; i < a.len(); i++) {
            a.set(i, (T) heap.get(i + 1));
        }
    }

    /**
     * 堆排序：下沉
     * @param heap 堆数组
     * @param x 需要下沉元素对应的下标
     * @param range 下沉范围
     * @param <T> 数组元素必须是可排序元素
     */
    private static <T extends Comparable<T>> void sink(Array<T> heap, int x, int range) {
        while (x * 2 <= range) {
            int maxIdx = x * 2 + 1 > range ? x * 2 : gt(heap, x * 2, x * 2 + 1) ? x * 2 : x * 2 + 1;
            if (lt(heap, x, maxIdx))
                Arrs.swap(heap, x, x = maxIdx);
            else
                return;
        }
    }
}
