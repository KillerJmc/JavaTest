package com.jmc.lang.math;

import java.math.BigInteger;
import java.util.Arrays;

public class Maths {
    /**
     * 计算阶乘
     * @param n 底数
     * @return 计算结果
     */
    public static BigInteger factorial(int n) {
        if (n == 0 || n == 1) return BigInteger.ONE;

        var a = new BigInteger[n];
        long tmp = 1;
        int p = 0;

        for (int i = 2; i <= n; i++) {
            if (tmp < Long.MAX_VALUE / i) {
                tmp *= i;
            } else {
                a[p++] = BigInteger.valueOf(tmp);
                tmp = i;
            }
        }
        a[p++] = BigInteger.valueOf(tmp);

        var optional = Arrays.stream(a, 0, p)
                                                .parallel()
                                                .reduce(BigInteger::multiply);
        return optional.orElse(null);
    }

    /**
     * 用斯特林公式计算阶乘结果的位数
     * @param n 底数
     * @return 阶乘结果的位数
     */
    public static long factorialLength(long n) {
        // lg n! = lg 2Πn / 2 + n lg n / e
        return n > Long.MAX_VALUE / 18 ? -1 : (long) (Math.log10(2 * Math.PI * n) / 2 + n * Math.log10(n / Math.E)) + 1;
    }
}
