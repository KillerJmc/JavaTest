package com.test.algorithm.graph;

import com.test.algorithm.graph.impl.DiGraph;

public abstract class DirectedCycleTemplate {
    protected abstract void dfs(DiGraph g, int v);
    public abstract boolean hasCycle();
}
