/*
 * 作者: Jmc
 * 时间: 2019.5.4
 * 功能: 产生指定随机数
 */
 
package com.jmc.array;

import java.util.*;

public class Rand
{
    private static final Random r = new Random(System.currentTimeMillis());
    
    public static int nextInt(int min, int max) {
        if (max < min) return -1;
        return r.nextInt(max - min + 1) + min;
    }
    
	//生成不同的随机数
        public static int[] getDiffRand(int min, int max, int n){
            //如果参数不合法就返回
            if (max < min || n > max - min + 1) return null;

            //rand数组
            int[] rand = new int[n];
            //已填入数字个数
            int amount = 0;

            //如果最小值为0就填充数组(排除默认值0的干扰)
            if (min == 0){
                for (int i = 0; i < n; i++) {
                    rand[i] = Integer.MAX_VALUE;
                }
            }

            //当已填入的数字个数小于用户指定的个数
            while (amount < n) {
                //生成用户指定范围的一个随机数
                int result = nextInt(min, max);
                //是否为新数字
                boolean flag = true;

                //遍历数组
                for (int oldNum : rand){
                    //如果不是新数字就跳出循环
                    if (result == oldNum){
                        flag = false;
                        break;
                    }
                }

                //如果为新数字就记录
                if (flag) {
                    rand[amount++] = result;
                }
            }
            return rand;
        }
}
