package com.test.algorithm.base.util;

/**
 * @author Jmc
 */
public class MyArr {
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
                for (var u : t) {
                    sb.append(u).append("\t");
                }
                sb.append("\n");
            }
        } else {
            sb.append("\t\t");

            for (int i = 0; i < a[0].length; i++) {
                sb.append(i).append("\t");
            }
            sb.append("\n")
                    .append("\t")
                    .append("---".repeat((int) (a[0].length * 1.5)))
                    .append("\n");

            for (int i = 0; i < a.length; i++) {
                sb.append(i).append("\t|\t");
                for (var t : a[i]) {
                    sb.append(t).append("\t");
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
