package com.test.algorithm.graph.interfaces.path;

import com.test.algorithm.graph.impl.graphs.Graph;
import com.test.algorithm.list.linked.impl.LinkedStack;

public abstract class DepthFirstPathsTemplate {
    protected abstract void dfs(Graph g, int v);
    public abstract boolean hasPathTo(int w);
    public abstract LinkedStack<Integer> pathTo(int w);
}
