package com.test.algorithm.base.bag;

import com.test.algorithm.base.util.MyArr;

import java.util.Arrays;

@SuppressWarnings("all")
public class Bag2 {
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

    public Bag2(int[] val, int[] vol) {
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
                dp[i][v] = v < vol[i] ? dp[i - 1][v] : Math.max(dp[i - 1][v], dp[i][v - vol[i]] + val[i]);

    }

    private int[] getOptimalResult() {
        int[] result = new int[types];
        for (int p = result.length - 1, i = types, v = volume; p >= 0;) {
            if (dp[i][v] == dp[i - 1][v]) {
                p--;
                i--;
            } else {
                result[p]++;
                v -= vol[i];
            }
        }
        return result;
    }

    private void print(int[] optimalResult) {
        System.out.printf("容量为%d的背包放入物品后最高价值是：%d\n", volume, dp[types][volume]);
        System.out.print("放进了");
        for (int i = 0; i < optimalResult.length; i++) {
            System.out.printf("第%d个物品%d个%s", i + 1, optimalResult[i], i != optimalResult.length - 1 ? ", " : "\n");
        }
    }

    public void printDp() {
        System.out.println(MyArr.toString(dp, true));
    }
}
