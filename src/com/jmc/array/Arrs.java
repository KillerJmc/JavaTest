package com.jmc.array;

import com.jmc.lang.extend.Rand;
import com.jmc.lang.extend.Tries;

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
        int p = 0;
        for (int i = newStartIdx; i < result.length; i++)
            result[i] = a[p++];
        return result;
    }

    /**
     * 获取数组的指定范围的元素的字符串形式
     * @param a 整型数组
     * @param fromInclusive 开始的索引（包括）
     * @param toInclusive 结束的索引（包括）
     * @return 结果字符串
     */
    public static String toString(int[] a, int fromInclusive, int toInclusive) {
        if (fromInclusive < 0 || toInclusive > a.length - 1 || fromInclusive > toInclusive)
            throw new ArrayIndexOutOfBoundsException(String.format("from : %d, to : %d, len = %d", fromInclusive, toInclusive, a.length));

        int[] result = new int[toInclusive - fromInclusive + 1];
        System.arraycopy(a, fromInclusive, result, 0, result.length);
        return Arrays.toString(result);
    }

    /**
     * 打印数组的指定范围的元素到字符串
     * @param a 整型数组
     * @param length 截取长度
     * @return 结果字符串
     */
    public static String toString(int[] a, int length) {
        return toString(a, 0, length - 1);
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
