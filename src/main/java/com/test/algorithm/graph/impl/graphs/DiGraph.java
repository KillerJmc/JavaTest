package com.test.algorithm.graph.impl.graphs;

import com.test.algorithm.graph.interfaces.graphs.DiGraphTemplate;
import com.test.algorithm.list.sequence.impl.SequenceQueue;

public class DiGraph extends DiGraphTemplate {
    private final int V;
    private int E;
    private final SequenceQueue<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public DiGraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = new SequenceQueue[V];
        for (int i = 0; i < adj.length; i++) adj[i] = new SequenceQueue<>();
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
    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public void addEdge(int... a) {
        for (int i = 0; i + 1 < a.length; i += 2)
            addEdge(a[i], a[i + 1]);
    }

    @Override
    public SequenceQueue<Integer> adj(int v) {
        return adj[v];
    }

    @Override
    protected DiGraph reverse() {
        var diGraph = new DiGraph(V);
        for (int i = 0; i < V; i++) {
            for (var t : this.adj(i)) {
                diGraph.addEdge(t, i);
            }
        }
        return diGraph;
    }
}
