/*
 * 作者: Jmc
 * 时间: 2019.5.4
 * 功能: 产生指定随机数
 */
 
package com.jmc.lang.extend;

import java.util.*;

public class Rand
{
    private static final Random r = new Random(System.currentTimeMillis());
    
    public static int nextInt(int minInclusive, int maxInclusive) {
        if (maxInclusive < minInclusive) return -1;
        return r.nextInt(maxInclusive - minInclusive + 1) + minInclusive;
    }
}
