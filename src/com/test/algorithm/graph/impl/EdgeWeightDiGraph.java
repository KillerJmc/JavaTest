package com.test.algorithm.graph.impl;

import com.test.algorithm.graph.impl.weight.DirectedEdge;
import com.test.algorithm.graph.impl.weight.Edge;
import com.test.algorithm.graph.interfaces.EdgeWeightDiGraphTemplate;
import com.test.algorithm.list.linked.impl.LinkedQueue;

public class EdgeWeightDiGraph implements EdgeWeightDiGraphTemplate {
    private final int V;
    private int E;
    private LinkedQueue<DirectedEdge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightDiGraph(int V) {
        this.V = V;
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
    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    public void addEdge(double... vs) {
        for (int i = 0; i + 2 < vs.length; i += 3) {
            addEdge(new DirectedEdge((int) vs[i], (int) vs[i + 1], vs[i + 2]));
        }
    }



    @Override
    public LinkedQueue<DirectedEdge> adj(int v) {
        return adj[v];
    }

    @Override
    public LinkedQueue<DirectedEdge> edges() {
        var edges = new LinkedQueue<DirectedEdge>();
        for (var q : adj) for (var e : q) edges.add(e);
        return edges;
    }
}
