package com.test.algorithm.graph;

import com.test.algorithm.graph.impl.Graph;

public abstract class DepthFirstSearchTemplate {
    protected abstract void dfs(Graph g, int v);

    public abstract boolean marked(int w);

    public abstract int count();
}
