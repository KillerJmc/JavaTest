package com.test.algorithm.graph.interfaces.weight;

import com.test.algorithm.graph.impl.weight.Edge;
import com.test.algorithm.graph.impl.weight.EdgeWeightedGraph;
import com.test.algorithm.list.linked.impl.LinkedQueue;

public abstract class PrimMSTTemplate {
    protected abstract void visit(EdgeWeightedGraph g, int v);
    public abstract LinkedQueue<Edge> edges();
}
