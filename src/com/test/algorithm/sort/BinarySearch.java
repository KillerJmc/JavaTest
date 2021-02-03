package com.test.algorithm.sort;

public class BinarySearch {
    public static int normalSearch(int[] a, int k) {
        int lo = 0, hi = a.length - 1, mid;
        while (hi >= lo) {
            mid = (lo + hi) / 2;
            if (a[mid] == k) return mid;
            else if (a[mid] > k) hi = mid - 1;
            else if (a[mid] < k) lo = mid + 1;
        }
        return -1;
    }

    /**
     * 插值查找算法
     * 适用于关键字值分布均匀的集合
     * @param a 数组
     * @param k 查找元素
     * @return 查找元素的下标
     */
    public static int interpolationSearch(int[] a, int k) {
        int lo = 0, hi = a.length - 1, mid;
        while (hi >= lo) {
            // 自适应选择
            mid = lo + (k - a[lo]) / (a[hi] - a[lo]) * (hi - lo);
            if (a[mid] == k) return mid;
            else if (a[mid] > k) hi = mid - 1;
            else if (a[mid] < k) lo = mid + 1;
        }
        return -1;
    }

    public static int searchFirst(int[] a, int k) {
        int lo = 0, hi = a.length - 1, mid;
        while (hi >= lo) {
            mid = (lo + hi) / 2;
            if (a[mid] == k) {
                if (mid == 0 || a[mid - 1] != k) return mid;
                else hi = mid - 1;
            }
            else if (a[mid] > k) hi = mid - 1;
            else if (a[mid] < k) lo = mid + 1;
        }
        return -1;
    }

    public static int searchLast(int[] a, int k) {
        int lo = 0, hi = a.length - 1, mid;
        while (hi >= lo) {
            mid = (lo + hi) / 2;
            if (a[mid] == k) {
                if (mid == a.length - 1 || a[mid + 1] != k) return mid;
                else lo = mid + 1;
            }
            else if (a[mid] > k) hi = mid - 1;
            else if (a[mid] < k) lo = mid + 1;
        }
        return -1;
    }

    public static int searchFirstGE(int[] a, int k) {
        int lo = 0, hi = a.length - 1, mid;
        while (hi >= lo) {
            mid = (lo + hi) / 2;
            if (a[mid] >= k) {
                if (mid == 0 || a[mid - 1] < k) return mid;
                else hi = mid - 1;
            }
            else if (a[mid] < k) lo = mid + 1;
        }
        return -1;
    }

    public static int searchLastLE(int[] a, int k) {
        int lo = 0, hi = a.length - 1, mid;
        while (hi >= lo) {
            mid = (lo + hi) / 2;
            if (a[mid] <= k) {
                if (mid == a.length - 1 || a[mid + 1] > k) return mid;
                else lo = mid + 1;
            }
            else if (a[mid] > k) hi = mid - 1;
        }
        return -1;
    }

}
