package com.test.algorithm.graph.interfaces.graphs;

import com.test.algorithm.graph.impl.edges.DirectedEdge;
import com.test.algorithm.list.linked.impl.LinkedQueue;

public interface EdgeWeightDiGraphTemplate {
    int V();
    int E();
    void addEdge(DirectedEdge e);
    LinkedQueue<DirectedEdge> adj(int v);
    LinkedQueue<DirectedEdge> edges();

}
