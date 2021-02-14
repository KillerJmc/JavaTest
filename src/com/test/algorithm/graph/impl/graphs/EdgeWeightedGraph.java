package com.test.algorithm.graph.impl.graphs;

import com.test.algorithm.graph.impl.edges.Edge;
import com.test.algorithm.graph.interfaces.graphs.EdgeWeightedGraphTemplate;
import com.test.algorithm.list.sequence.impl.SequenceQueue;

public class EdgeWeightedGraph implements EdgeWeightedGraphTemplate {
    private final int V;
    private int E;
    private final SequenceQueue<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
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
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.another(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public void addEdge(double... vs) {
        for (int i = 0; i + 2 < vs.length; i += 3) {
            addEdge(new Edge((int) vs[i], (int) vs[i + 1], vs[i + 2]));
        }
    }

    @Override
    public SequenceQueue<Edge> adj(int v) {
        return adj[v];
    }

    @Override
    public SequenceQueue<Edge> edges() {
        var queue = new SequenceQueue<Edge>();
        for (int v = 0; v < V; v++)
            for (var e : adj(v))
                if (v < e.another(v)) queue.add(e);

        return queue;
    }
}
