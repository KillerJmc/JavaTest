package com.test.apply.math;

import com.jmc.array.Arrs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Arrangement {
    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++)
        {
            String result = getResult();
            if (result != null)
            {
                if (set.add(result)) {
                    System.out.println();
                    System.out.println(result);
                    System.out.println();
                    System.out.println(set.size());
                }
            }
        }
    }

    @SuppressWarnings("all")
    private static String getResult() {
        int idx_甲 = 0, idx_乙 = 0, idx_丙 = 0, idx_丁 = 0;
        int[] a = Arrs.getDiffRandArr(1, 7, 7);

        for (int i = 0; i < 7; i++) {
            switch (a[i]) {
                case 1 -> idx_甲 = i;
                case 2 -> idx_乙 = i;
                case 3 -> idx_丙 = i;
                case 4 -> idx_丁 = i;
            }
        }

        boolean 甲乙丙不相邻且甲丁相邻 =
                Math.abs(idx_甲 - idx_乙) != 1 &&
                Math.abs(idx_甲 - idx_丙) != 1 &&
                Math.abs(idx_乙 - idx_丙) != 1 &&
                Math.abs(idx_甲 - idx_丁) == 1;

        return 甲乙丙不相邻且甲丁相邻 ? Arrays.toString(a) : null;
    }

}
