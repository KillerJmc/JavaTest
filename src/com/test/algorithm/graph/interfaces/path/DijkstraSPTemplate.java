package com.test.algorithm.graph.interfaces.path;

import com.test.algorithm.graph.impl.EdgeWeightDiGraph;
import com.test.algorithm.graph.impl.weight.DirectedEdge;
import com.test.algorithm.graph.impl.weight.EdgeWeightedGraph;
import com.test.algorithm.list.linked.impl.LinkedQueue;

public abstract class DijkstraSPTemplate {
    protected abstract void relax(EdgeWeightDiGraph g, int v);
    public abstract double distTo(int v);
    public abstract boolean hasPathTo(int v);
    public abstract LinkedQueue<DirectedEdge> pathTo(int v);
}
