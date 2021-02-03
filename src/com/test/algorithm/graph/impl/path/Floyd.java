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
