package com.test.algorithm.graph;

import com.test.algorithm.graph.impl.Graph;

public abstract class BreadthFirstSearchTemplate {
    protected abstract void bfs(Graph g, int v);

    public abstract boolean marked(int w);

    public abstract int count();
}
