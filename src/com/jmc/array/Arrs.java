package com.jmc.array;

import com.jmc.lang.extend.Rand;
import com.jmc.lang.extend.Tries;
import com.jmc.util.Compare;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Arrs {
    /**
     * 获得指定类型的初始化完成的数组
     * @param c 指定类型的Class对象
     * @param len 数组长度
     * @param <T> 指定的类型
     * @return 初始化完的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(Class<T> c, int len) {
        return Tries.tryReturnsT(() -> {
            var a = (T[]) Array.newInstance(c, len);
            var ctr = c.getConstructor();

            for (int i = 0; i < a.length; i++) a[i] = ctr.newInstance();
            return a;
        });
    }

    /**
     * 包装基本数据类型数组为可排序对象数组
     * @param a 原数组
     * @return 结果数组
     */
    @SuppressWarnings("rawtypes")
    public static Comparable[] box(Object a) {
        if (a instanceof int[]) {
            return Arrays.stream((int[]) a)
                    .parallel()
                    .boxed()
                    .toArray(Comparable[]::new);
        } else if (a instanceof long[]) {
            return Arrays.stream((long[]) a)
                    .parallel()
                    .boxed()
                    .toArray(Comparable[]::new);
        } else if (a instanceof double[]) {
            return Arrays.stream((double[]) a)
                    .parallel()
                    .boxed()
                    .toArray(Comparable[]::new);
        } else if (a instanceof byte[] byte_a) {
            var result = new Comparable[byte_a.length];
            for (int i = 0; i < byte_a.length; i++)
                result[i] = byte_a[i];
            return result;
        } else if (a instanceof char[] char_a) {
            var result = new Comparable[char_a.length];
            for (int i = 0; i < char_a.length; i++)
                result[i] = char_a[i];
            return result;
        } else if (a instanceof short[] short_a) {
            var result = new Comparable[short_a.length];
            for (int i = 0; i < short_a.length; i++)
                result[i] = short_a[i];
            return result;
        } else if (a instanceof float[] float_a) {
            var result = new Comparable[float_a.length];
            for (int i = 0; i < float_a.length; i++)
                result[i] = float_a[i];
            return result;
        } else if (a instanceof boolean[] boolean_a) {
            var result = new Comparable[boolean_a.length];
            for (int i = 0; i < boolean_a.length; i++)
                result[i] = boolean_a[i];
            return result;
        } else if (a instanceof Comparable[] comp_a){
            return comp_a;
        } else {
            throw new IllegalArgumentException("array a is invalid!");
        }
    }

    /**
     * 交换元素
     * @param a 数组名
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     * @param <T> 数组元素
     */
    public static <T> void swap(T[] a, int idx1, int idx2) {
        var tmp = a[idx1];
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
     * 交换元素
     * @param a 长整形数组
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     */
    public static void swap(long[] a, int idx1, int idx2) {
        var tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }

    /**
     * 交换元素
     * @param a 短整形数组
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     */
    public static void swap(short[] a, int idx1, int idx2) {
        var tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }

    /**
     * 交换元素
     * @param a 单精度浮点数数组
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     */
    public static void swap(float[] a, int idx1, int idx2) {
        var tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }

    /**
     * 交换元素
     * @param a 双精度浮点数数组
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     */
    public static void swap(double[] a, int idx1, int idx2) {
        var tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }

    /**
     * 交换元素
     * @param a 字节类型数组
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     */
    public static void swap(byte[] a, int idx1, int idx2) {
        var tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }

    /**
     * 交换元素
     * @param a 字符数组
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     */
    public static void swap(char[] a, int idx1, int idx2) {
        var tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }

    /**
     * 交换元素
     * @param a 布尔值数组
     * @param idx1 第一个元素对应的下标
     * @param idx2 第二个元素对应的下标
     */
    public static void swap(boolean[] a, int idx1, int idx2) {
        var tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }

    /**
     * 比较是否大于
     * @param a 可排序数组
     * @param idx1 第一个元素的下标
     * @param idx2 第二个元素的下标
     * @return 第一个元素是否大于第二个元素
     */
    public static <T extends Comparable<T>> boolean greater(T[] a, int idx1, int idx2) {
        return Compare.greater(a[idx1], a[idx2]);
    }

    /**
     * 比较是否大于或等于
     * @param a 可排序数组
     * @param idx1 第一个元素的下标
     * @param idx2 第二个元素的下标
     * @return 第一个元素是否大于或等于第二个元素
     */
    public static <T extends Comparable<T>> boolean greaterOrEquals(T[] a, int idx1, int idx2) {
        return Compare.greaterOrEquals(a[idx1], a[idx2]);
    }

    /**
     * 比较是否小于
     * @param a 可排序数组
     * @param idx1 第一个元素的下标
     * @param idx2 第二个元素的下标
     * @return 第一个元素是否小于第二个元素
     */
    public static <T extends Comparable<T>> boolean less(T[] a, int idx1, int idx2) {
        return Compare.less(a[idx1], a[idx2]);
    }

    /**
     * 比较是否小于或等于
     * @param a 可排序数组
     * @param idx1 第一个元素的下标
     * @param idx2 第二个元素的下标
     * @return 第一个元素是否小于或等于第二个元素
     */
    public static <T extends Comparable<T>> boolean lessOrEquals(T[] a, int idx1, int idx2) {
        return Compare.lessOrEquals(a[idx1], a[idx2]);
    }

    /**
     * 比较是否等于
     * @param a 可排序数组
     * @param idx1 第一个元素的下标
     * @param idx2 第二个元素的下标
     * @return 第一个元素是否等于第二个元素
     */
    public static <T extends Comparable<T>> boolean equals(T[] a, int idx1, int idx2) {
        return Compare.equals(a[idx1], a[idx2]);
    }

    /**
     * 获取最大值
     * @param a 整型数组
     * @return 数组最大值
     */
    public static int max(int[] a) {
        return a[maxIdx(a)];
    }


    /**
     * 获取最大值
     * @param a 长整型数组
     * @return 数组最大值
     */
    public static long max(long[] a) {
        return a[maxIdx(a)];
    }

    /**
     * 获取最大值
     * @param a double数组
     * @return 数组最大值
     */
    public static double max(double[] a) {
        return a[maxIdx(a)];
    }

    /**
     * 获取最小值
     * @param a 整型数组
     * @return 数组最大值
     */
    public static int min(int[] a) {
        return a[minIdx(a)];
    }

    /**
     * 获取最小值
     * @param a 长整型数组
     * @return 数组最大值
     */
    public static long min(long[] a) {
        return a[minIdx(a)];
    }

    /**
     * 获取最小值
     * @param a double数组
     * @return 数组最大值
     */
    public static double min(double[] a) {
        return a[minIdx(a)];
    }

    /**
     * 获取最大值对应的下标
     * @param a int数组
     * @return 数组最大值
     */
    public static int maxIdx(int[] a) {
        int maxIdx = 0;
        for (int i = 1; i < a.length; i++) if (a[i] > a[maxIdx]) maxIdx = i;
        return maxIdx;
    }

    /**
     * 获取最大值对应的下标
     * @param a long数组
     * @return 数组最大值
     */
    public static int maxIdx(long[] a) {
        int maxIdx = 0;
        for (int i = 1; i < a.length; i++) if (a[i] > a[maxIdx]) maxIdx = i;
        return maxIdx;
    }

    /**
     * 获取最大值对应的下标
     * @param a double数组
     * @return 数组最大值
     */
    public static int maxIdx(double[] a) {
        int maxIdx = 0;
        for (int i = 1; i < a.length; i++) if (a[i] > a[maxIdx]) maxIdx = i;
        return maxIdx;
    }

    /**
     * 获取最小值对应的下标
     * @param a int数组
     * @return 数组最小值
     */
    public static int minIdx(int[] a) {
        int minIdx = 0;
        for (int i = 1; i < a.length; i++) if (a[i] < a[minIdx]) minIdx = i;
        return minIdx;
    }

    /**
     * 获取最小值对应的下标
     * @param a long数组
     * @return 数组最小值
     */
    public static int minIdx(long[] a) {
        int minIdx = 0;
        for (int i = 1; i < a.length; i++) if (a[i] < a[minIdx]) minIdx = i;
        return minIdx;
    }

    /**
     * 获取最小值对应的下标
     * @param a double数组
     * @return 数组最小值
     */
    public static int minIdx(double[] a) {
        int minIdx = 0;
        for (int i = 1; i < a.length; i++) if (a[i] < a[minIdx]) minIdx = i;
        return minIdx;
    }

    /**
     * 生成不同的随机数
     * @param min 最小值
     * @param max 最大值
     * @param n 生成个数
     * @return 生成结果的数组
     */
    public static int[] getDiffRandArr(int min, int max, int n){
        // 如果参数不合法就返回
        if (max < min || n > max - min + 1) return null;

        // rand数组
        int[] rand = new int[n];

        // 已填入数字个数
        int amount = 0;

        // 如果最小值为0就填充数组(排除默认值0的干扰)
        if (min == 0) Arrays.fill(rand, Integer.MAX_VALUE);

        // 当已填入的数字个数小于用户指定的个数
        outer : while (amount < n) {
            int result = Rand.nextInt(min, max);

            // 遍历数组
            for (int oldNum : rand) if (result == oldNum) continue outer;

            // 如果为新数字就记录
            rand[amount++] = result;
        }

        return rand;
    }

    /**
     * 获取随机数组
     * @param min 最小值
     * @param max 最大值
     * @param n 数组长度
     * @return 结果数组
     */
    public static int[] getRandArr(int min, int max, int n){
        // 如果参数不合法就返回
        if (max < min) return null;

        // rand数组
        int[] rand = new int[n];

        for (int i = 0; i < rand.length; i++) rand[i] = Rand.nextInt(min, max);

        return rand;
    }

    public static int[] copyOf(int[] a, int newStartIdx) {
        int[] result = new int[a.length + newStartIdx];
        System.arraycopy(a, 0, result, newStartIdx, result.length);
        return result;
    }

    /**
     * 返回指定个数的子集合的字符串形式
     * @param a 数组
     * @param limit 子集合元素个数
     * @return 结果字符串
     */
    public static String toString(int[] a, int limit) {
        return Arrays.toString(Arrays.copyOf(a, limit));
    }

    /**
     * 打印二维整型数组到字符串
     * @param a 整型数组
     * @param showDetails 是否打印细节（索引）
     * @return 打印的结果字符串
     */
    public static String toString(int[][] a, boolean showDetails) {
        var sb = new StringBuilder();

        if (!showDetails) {
            for (var t : a) {
                for (var u : t) sb.append(u).append("\t");
                sb.append("\n");
            }
        } else {
            sb.append("\t\t");

            for (int i = 0; i < a[0].length; i++) sb.append(i).append("\t");
            sb.append("\n")
              .append("\t")
              .append("---".repeat((int) (a[0].length * 1.5)))
              .append("\n");

            for (int i = 0; i < a.length; i++) {
                sb.append(i).append("\t|\t");
                for (var t : a[i]) sb.append(t).append("\t");
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
