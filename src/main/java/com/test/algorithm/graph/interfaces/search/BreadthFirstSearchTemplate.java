package com.test.algorithm.graph.interfaces.search;

import com.test.algorithm.graph.impl.graphs.Graph;

public abstract class BreadthFirstSearchTemplate {
    protected abstract void bfs(Graph g, int v);

    public abstract boolean marked(int w);

    public abstract int count();
}
