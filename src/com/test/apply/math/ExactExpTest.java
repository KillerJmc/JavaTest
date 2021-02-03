package com.test.apply.math;

import com.jmc.lang.math.ExactExp;
import com.jmc.lang.timer.Timers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ExactExpTest {
    public static void main(String[] args) throws Exception {
        int n = 10000;
        Timers.milliTimer(() -> useExp(n), "useExp");
        System.out.println("\n--------------------------------------------\n");
        Timers.milliTimer(() -> useNormal(n), "useNormal");
    }

    public static void useExp(int n) {
        // n! ~ √(2Πn) * (n / e) ^ n
        String exp = "√(2 * %.16f * %d) * (%d / %.16f) ^ %d".formatted(Math.PI, n, n, Math.E, n);
        var result = ExactExp.getResult(exp, 0);

        System.out.println(result);
    }

    public static void useNormal(int n) {
        // n! ~ √(2Πn) * (n / e) ^ n
        var result2 = new BigDecimal(2).multiply(new BigDecimal(Double.valueOf(Math.PI).toString()))
                .multiply(new BigDecimal(n)).sqrt(MathContext.DECIMAL128)
                .multiply(new BigDecimal(n).divide(new BigDecimal(Double.valueOf(Math.E).toString()), 34, RoundingMode.HALF_UP)
                        .pow(n)).setScale(0, RoundingMode.HALF_UP);

        System.out.println(result2);
    }
}
