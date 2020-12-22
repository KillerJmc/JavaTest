package com.test.algorithm.utils;

public class ArrayUtils {
    /**
     * 获取未知数组长度
     * @param a 未知类型数组
     * @return 数组长度
     */
    public static int len(Object a) {
        return switch (a.getClass().getSimpleName()) {
            case "byte[]" -> ((byte[])a).length;
            case "char[]" -> ((char[])a).length;
            case "short[]" -> ((short[])a).length;
            case "int[]" -> ((int[])a).length;
            case "long[]" -> ((long[])a).length;
            case "float[]" -> ((float[])a).length;
            case "double[]" -> ((double[])a).length;
            default -> ((Object[])a).length;
        };
    }

    /**
     * 返回数组中指定下标的元素
     * @param a 数组
     * @param idx 指定的下标
     * @return 数组中指定下标的元素
     */
    public static Object get(Object a, int idx) {
        return switch (a.getClass().getSimpleName()) {
            case "byte[]" -> ((byte[])a)[idx];
            case "char[]" -> ((char[])a)[idx];
            case "short[]" -> ((short[])a)[idx];
            case "int[]" -> ((int[])a)[idx];
            case "long[]" -> ((long[])a)[idx];
            case "float[]" -> ((float[])a)[idx];
            case "double[]" -> ((double[])a)[idx];
            default -> ((Object[])a)[idx];
        };
    }

    /**
     * 将元素放入数组指定下标的位置
     * @param a 数组
     * @param idx 指定的下标
     */
    public static void set(Object a, int idx, Object value) {
        switch (a.getClass().getSimpleName()) {
            case "byte[]" -> ((byte[])a)[idx] = (byte) value;
            case "char[]" -> ((char[])a)[idx] = (char) value;
            case "short[]" -> ((short[])a)[idx] = (short) value;
            case "int[]" -> ((int[])a)[idx] = (int) value;
            case "long[]" -> ((long[])a)[idx] = (long) value;
            case "float[]" -> ((float[])a)[idx] = (float) value;
            case "double[]" -> ((double[])a)[idx] = (double) value;
            default -> ((Object[])a)[idx] = value;
        };
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
     * 比较是否大于或等于
     * @param o1 第一个元素
     * @param o2 第二个元素
     * @param <O> 可排序元素
     * @return 第一个元素是否大于或等于第二个元素
     */
    public static <O extends Comparable<O>> boolean greaterOrEquals(O o1, O o2) {
        return o1.compareTo(o2) >= 0;
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
     * 比较是否小于或等于
     * @param o1 第一个元素
     * @param o2 第二个元素
     * @param <O> 可排序元素
     * @return 第一个元素是否小于第二个元素
     */
    public static <O extends Comparable<O>> boolean lessOrEquals(O o1, O o2) {
        return o1.compareTo(o2) < 0;
    }

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
    }

    /**
     * 交换元素
     * @param a 整形数组
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     */
    public static void swap(int[] a, int idx1, int idx2) {
        var tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }

    /**
     * 返回较大数对应的下标
     * @param a 数组名
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     * @param <T> 可排序元素
     * @return 较大数对应的下标
     */
    public static <T extends Comparable<T>> int greaterIdx(T[] a, int idx1, int idx2) {
        return greater(a[idx1], a[idx2]) ? idx1 : idx2;
    }

    /**
     * 返回较小数对应的下标
     * @param a 数组名
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     * @param <T> 可排序元素
     * @return 较小数对应的下标
     */
    public static <T extends Comparable<T>> int lessIdx(T[] a, int idx1, int idx2) {
        return less(a[idx1], a[idx2]) ? idx1 : idx2;
    }
}
