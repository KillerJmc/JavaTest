package com.test.algorithm.base.bag;

import com.jmc.array.Arrs;

import java.util.Arrays;

public class Bag {
    /**
     * store the value of every item
     */
    private final int[] val;

    /**
     * store the volume of every item
     */
    private final int[] vol;

    /**
     * items types
     */
    private final int types;

    /**
     * dp[i][v]: the max value of the first i items (includes i) in the case of volume of v
     */
    private int[][] dp;

    /**
     * the specific volume size
     */
    private int volume;

    public Bag(int[] val, int[] vol) {
        this.types = val.length;
        this.val = Arrays.copyOfRange(val, 1, val.length);
        this.vol = Arrays.copyOfRange(vol, 1, vol.length);
    }

    public void solve(int volume) {
        this.volume = volume;
        fillDp();
        print(getOptimalResult());
    }


    private void fillDp() {
        this.dp = new int[types + 1][volume + 1];
        for (int i = 1; i <= types; i++)
            for (int v = 1; v <= volume; v++)
                dp[i][v] = v < vol[i] ? dp[i - 1][v] : Math.max(dp[i - 1][v], dp[i - 1][v - vol[i]] + val[i]);
    }

    private int[] getOptimalResult() {
        int[] result = new int[types];
        for (int p = result.length - 1, i = types, v = volume; p >= 0; p--) {
            if (dp[i][v] == dp[i - 1][v]) {
                result[p] = 0;
            } else {
                result[p] = 1;
                v -= vol[i];
            }
            i--;
        }
        return result;
    }

    private void print(int[] optimalResult) {
        System.out.printf("容量为%d的背包放入物品后最高价值是：%d\n", volume, dp[types][volume]);
        System.out.print("放进了第");
        for (int i = 0; i < optimalResult.length; i++) {
            if (optimalResult[i] == 1) System.out.print(i + 1 + (i == optimalResult.length - 1 ? "个物品\n" : ", "));
        }
    }

    public void printDp() {
        System.out.println(Arrs.toString(dp, true));
    }
}
