package com.test.algorithm.graph.impl.path;

import com.jmc.array.Arrs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Dijkstra {
    private final int V;
    private final boolean[] visited;
    private final double[] distTo;
    private final int[] edgeTo;

    public Dijkstra(Graph g, int V) {
        this.V = V;
        this.visited = new boolean[g.E];
        this.edgeTo = new int[g.E];
        this.distTo = new double[g.E];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[V] = 0;

        int w;
        while ((w = minEdge()) != -1) {
            visited[w] = true;
            for (Edge e : g.adj(w)) {
                relax(e);
            }
        }
    }

    /**
     * 松弛，即把目前的路径长度和先前的对比，比先前路径短就替换
     * @param e 所松弛的边
     */
    private void relax(Edge e) {
        int v = e.from(), w = e.to();
        double weight = e.weight();
        if (distTo[v] + weight < distTo[w]) {
            distTo[w] = distTo[v] + weight;
            edgeTo[w] = v;
        }
    }

    /**
     * 贪婪算法，获取一个顶点的邻接表（一次更新后）所连的最短边（对应的右顶点）
     * @return 所求右顶点
     */
    private int minEdge() {
        int minIdx = -1;
        double minWeight = Double.POSITIVE_INFINITY;
        for (int i = 0; i < distTo.length; i++) {
            if (!visited[i]) {
                if (distTo[i] < minWeight) {
                    minIdx = i;
                    minWeight = distTo[i];
                }
            }
        }
        return minIdx;
    }

    public Stack<Integer> pathTo(int v) {
        if (v == V) return new Stack<>() {{add(V);}};
        var result = new Stack<Integer>();

        result.push(v);
        while ((v = edgeTo[v]) != V) result.push(v);
        result.push(V);

        return result;
    }


    public record Edge(int from, int to, double weight) {}

    @SuppressWarnings({"all"})
    public static class Graph {
        private final ArrayList[] adj;
        private final int E;

        public Graph(int E) {
            this.E = E;
            this.adj = Arrs.newInstance(ArrayList.class, E);
        }

        public void addEdge(Edge e) {
            adj[e.from].add(e);
        }

        public void addEdges(double... a) {
            for (int i = 0; i + 2 < a.length; i += 3)
                addEdge(new Edge((int) a[i], (int) a[i + 1], a[i + 2]));
        }

        public ArrayList<Edge> adj(int from) {
            return adj[from];
        }
    }
}
