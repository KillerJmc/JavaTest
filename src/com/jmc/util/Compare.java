package com.jmc.util;

public class Compare {
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
     * 比较是否等于
     * @param o1 第一个元素
     * @param o2 第二个元素
     * @param <O> 可排序元素
     * @return 第一个元素是否大于第二个元素
     */
    public static <O extends Comparable<O>> boolean equals(O o1, O o2) {
        return o1.compareTo(o2) == 0;
    }

}
