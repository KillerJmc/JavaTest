package com.test.algorithm.graph.interfaces.topological;

import com.test.algorithm.graph.impl.graphs.DiGraph;

public abstract class DirectedCycleTemplate {
    protected abstract void dfs(DiGraph g, int v);
    public abstract boolean hasCycle();
}
