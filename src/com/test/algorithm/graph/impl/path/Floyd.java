package com.test.algorithm.graph.impl.path;

import com.jmc.array.Arrs;
import com.jmc.lang.extend.Outs;

import java.util.Arrays;

/**
 * 弗洛伊德算法可以求出任意两个点之间的最短距离
 */
public class Floyd {
    private final char[] vertex;
    private final int[][] dis;
    /**
     * 前驱表（i通过pre[i][k]到达k）
     */
    private final int[][] pre;

    public Floyd(char[] vertex, int[][] dis) {
        this.vertex = vertex;
        this.dis = dis;
        this.pre = new int[vertex.length][vertex.length];
        for (int i = 0; i < pre.length; i++) Arrays.fill(pre[i], i);
    }

    public void solve() {
        int len;
        // k：中间顶点，i：顶点，j：到达的顶点
        // k放最外面：这个算法源于动态规划f[k][i][j]
        // 其中f[k][i][j]由f[k-1][i][j]而来，需要k-1作为中间节点松弛才能保证k的距离最小

        // 比如
        //                   4
        //                  ^ \
        //                 /  (2)
        //                (3)   \
        //               /       v
        //    3 --(6)-> 5 --(10)--> 1 --(1)--> 2

        // 设f[i, k, j] = distance

        // (1) 如果把k放最外层，则先会计算▲f[5, 4, 1] = f[5, 5, 4] + f[4, 4, 1] = 3 + 2 = 5
        //     再计算 重要：f[3, 5, 1] = f[3, 3, 5] + ▲f[5, 4, 1] = 6 + 5 = 11
        // (2) 如果把k放最里层，则先会计算 重要：f[3, 5, 1] = f[3, 3, 5] + f[5, 5, 1] = 6 + 10 = 16
        //     再计算f[5, 4, 1] = f[5, 5, 4] + f[4, 4, 1] = 3 + 2 = 5

        // 从而导致f[3, 5, 1]因为提前算而产生错误

        // 意思即：f[k][i][j] = min(f[k - 1][i][j], f[k - 1][i][k] + f[k - 1][k][j])
        for (int k = 0; k < dis.length; k++) {
            for (int i = 0; i < dis.length; i++) {
                for (int j = 0; j < dis.length; j++) {
                    // 求出从i出发经过k中间顶点到j的距离
                    len = dis[i][k] + dis[k][j];
                    if (len < dis[i][j]) {
                        // 更新距离表
                        dis[i][j] = len;
                        // 更新前驱表
                        pre[i][j] = pre[k][j];
                    }
                }
            }
        }
    }

    public void show() {
        var disSplit = Arrs.toString(dis, true).split("\n");
        String firstLine = disSplit[0];
        for (var s : firstLine.split("\t")) {
            if (!s.isEmpty()) {
                int t = Integer.parseInt(s);
                firstLine = firstLine.replace(s, vertex[t] + "");
            }
        }
        disSplit[0] = firstLine;
        for (int i = 2; i < disSplit.length; i++) {
            int idx = disSplit[i].charAt(0) - '0';
            disSplit[i] = vertex[idx] + disSplit[i].substring(1);
        }

        var disStr = String.join("\n", disSplit);
        System.out.println(disStr);
        System.out.println();
        Outs.newLine();

        var preStr = Arrs.toString(pre, true);
        for (int i = 0; i < preStr.split("\n").length - 2; i++) {
            preStr = preStr.replace(i + "", vertex[i] + "");
        }
        System.out.println();
        System.out.println(preStr);
        Outs.newLine();
    }
}
