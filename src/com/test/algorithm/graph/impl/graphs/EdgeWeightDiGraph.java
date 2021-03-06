package com.test.algorithm.graph.impl.graphs;

import com.jmc.array.Arrs;
import com.test.algorithm.graph.impl.edges.DirectedEdge;
import com.test.algorithm.graph.interfaces.graphs.EdgeWeightDiGraphTemplate;
import com.test.algorithm.list.linked.impl.LinkedQueue;

public class EdgeWeightDiGraph implements EdgeWeightDiGraphTemplate {
    private final int V;
    private int E;
    private final LinkedQueue<DirectedEdge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightDiGraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = Arrs.newInstance(LinkedQueue.class, V);
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
