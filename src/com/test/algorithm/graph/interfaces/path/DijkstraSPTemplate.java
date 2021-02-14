package com.test.algorithm.graph.interfaces.path;

import com.test.algorithm.graph.impl.graphs.EdgeWeightDiGraph;
import com.test.algorithm.graph.impl.edges.DirectedEdge;
import com.test.algorithm.list.linked.impl.LinkedQueue;

public abstract class DijkstraSPTemplate {
    protected abstract void relax(EdgeWeightDiGraph g, int v);
    public abstract double distTo(int v);
    public abstract boolean hasPathTo(int v);
    public abstract LinkedQueue<DirectedEdge> pathTo(int v);
}
