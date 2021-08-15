package com.test.main;

import com.jmc.array.Array;
import com.jmc.array.Arrs;
import com.jmc.lang.timer.Timers;
import com.test.algorithm.sort.Sort;

import java.util.Arrays;

/**
 * @author Jmc
 */
public class Main {
    public static void main(String[] args) {
        int[] intArray = Arrs.getRandArr(-100000000, 100000000, 100000000);
        int[] intArray2 = intArray.clone();
        int[] intArray3 = intArray.clone();

        System.out.println("测试数据：一亿个随机数字的倒置");

//        Timers.milliTimer(() -> Sort.reverse(intArray2), "int数组 ");

        Timers.milliTimer(() -> Sort.reverse(Array.of(intArray)), "新的通用数组 ");

        System.out.print(Arrays.equals(intArray, intArray2));
    }
}

