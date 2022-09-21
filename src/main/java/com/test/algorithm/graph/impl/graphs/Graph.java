package com.test.algorithm.graph.impl.graphs;

import com.test.algorithm.graph.interfaces.graphs.GraphTemplate;
import com.test.algorithm.list.linked.impl.LinkedQueue;

public class Graph implements GraphTemplate {
    /**
     * vertex: 顶点
     */
    private final int V;

    /**
     * edge: 边
     */
    private int E;

    /**
     * adjacency list: 邻接表
     */
    private final LinkedQueue<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public Graph(int v) {
        this.V = v;
        this.E = 0;
        this.adj = new LinkedQueue[V];
        for (int i = 0; i < adj.length; i++) adj[i] = new LinkedQueue<>();
    }

    @Override
    public int V() {
        return V;
    }

    @Override
    public int E() {
        return E;
    }

    @Override
    public void addEdge(int v1, int v2) {
        adj[v1].add(v2);
        adj[v2].add(v1);
        E++;
    }

    public void addEdge(int... a) {
        for (int i = 0; i + 1 < a.length; i += 2)
            addEdge(a[i], a[i + 1]);
    }

    @Override
    public LinkedQueue<Integer> adj(int v) {
        return adj[v];
    }
}
