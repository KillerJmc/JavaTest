package com.test.algorithm.graph.impl.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * 单文件实现Dijkstra算法
 * 作者：Jmc
 */
public class SingleDijkstraSP {
    public static class DijkstraSP {
        private final Edge[] edgeTo;
        private final double[] distTo;
        private final TreeSet<IdxAndWeight> pq;

        @SuppressWarnings("all")
        public DijkstraSP(Graph g, int v) {
            this.edgeTo = new Edge[g.V()];
            this.distTo = new double[g.V()];
            Arrays.fill(distTo, Double.POSITIVE_INFINITY);

            distTo[v] = 0;
            this.pq = new TreeSet<>();
            pq.add(new IdxAndWeight(v, 0));

            while (!pq.isEmpty()) relax(g, pq.pollFirst().idx);
        }

        private void relax(Graph g, int v) {
            for (var e : g.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    distTo[w] = distTo[v] + e.weight();
                    edgeTo[w] = e;
                    pq.add(new IdxAndWeight(w, distTo[w]));
                }
            }
        }

        public double distTo(int v) {
            return distTo[v];
        }

        public boolean hasPathTo(int v) {
            return distTo(v) != Double.POSITIVE_INFINITY;
        }

        public List<Edge> pathTo(int v) {
            if (!hasPathTo(v)) return null;

            var edges = new ArrayList<Edge>();
            Edge e;
            while ((e = edgeTo[v]) != null) {
                edges.add(e);
                v = e.from();
            }
            return edges;
        }
    }

    private static record IdxAndWeight(int idx, double weight) implements Comparable<IdxAndWeight> {
        @Override
        public int compareTo(IdxAndWeight o) {
            return Double.compare(weight, o.weight());
        }
    }

    public static class Graph {
        private final int V;
        private int E;
        private final List<Edge>[] adj;

        @SuppressWarnings("unchecked")
        public Graph(int V) {
            this.V = V;
            this.E = 0;
            this.adj = new List[V];
            for (int i = 0; i < adj.length; i++) adj[i] = new ArrayList<>();
        }

        public int V() {
            return V;
        }

        public int E() {
            return E;
        }

        public List<Edge> adj(int v) {
            return adj[v];
        }

        public void addEdge(Edge e) {
            adj[e.from()].add(e);
            E++;
        }

        public void addEdge(Object... a) {
            for (int i = 0; i + 2 < a.length; i += 3) {
                addEdge(new Edge((int) a[i], (int) a[i + 1], (double) a[i + 2]));
            }
        }
    }

    public static record Edge(int v, int w, double weight) {
        public int from() {
            return v;
        }

        public int to() {
            return w;
        }
    }
}
