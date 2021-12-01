package com.test.algorithm.graph.impl.path;

import java.util.Arrays;

public class Dijkstra {
    /**
     * 解决方案
     * @param g 邻接矩阵
     * @param V 节点数量
     * @param begin 开始节点
     */
    public static void solve(int[][] g, int V, int begin) {
        // *开始节点到各节点的最短距离
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // 开始节点到自身距离为0
        dist[begin] = 0;

        // *路径记录（从开始节点到idx的上一个节点）
        int[] path = new int[V];

        // 节点是否被访问过
        boolean[] visited = new boolean[V];

        while (true) {
            int minIdx = -1, minValue = Integer.MAX_VALUE;

            // 找出下个遍历的最小节点，该节点以下满足2点要求：
            // 1. 没有被访问过
            // 2. 与开始节点的距离最短
            for (int i = 0; i < V; i++) {
                if (!visited[i] && dist[i] < minValue) {
                    minIdx = i;
                    minValue = dist[i];
                }
            }

            // 找不到就完成算法，退出循环
            if (minIdx == -1) {
                break;
            }

            // 标记节点已经被访问过
            visited[minIdx] = true;

            // 遍历所有节点，分别替换所有与最小节点邻接的点的距离（小于当前就替换）
            for (int i = 0; i < V; i++) {
                // 判断是否邻接，邻接才能替换
                if (g[minIdx][i] != Integer.MAX_VALUE) {
                    if (dist[minIdx] + g[minIdx][i] < dist[i]) {
                        dist[i] = dist[minIdx] + g[minIdx][i];

                        // 到达i的此时最佳顶点下标是minIdx
                        path[i] = minIdx;
                    }
                }
            }
        }

        System.out.println("dist: " + Arrays.toString(dist));
        System.out.println("path: " + Arrays.toString(path));
    }
}
