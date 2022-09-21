package com.test.algorithm.graph.interfaces.graphs;

import com.test.algorithm.graph.impl.edges.Edge;
import com.test.algorithm.list.sequence.impl.SequenceQueue;

public interface EdgeWeightedGraphTemplate {
    int V();
    int E();
    void addEdge(Edge e);
    SequenceQueue<Edge> adj(int v);
    SequenceQueue<Edge> edges();
}
