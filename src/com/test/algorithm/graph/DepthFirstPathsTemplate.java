package com.test.algorithm.graph;

import com.test.algorithm.graph.impl.Graph;
import com.test.algorithm.list.linked.impl.LinkedStack;
import com.test.algorithm.list.sequence.impl.SequenceStack;

public abstract class DepthFirstPathsTemplate {
    protected abstract void dfs(Graph g, int v);
    public abstract boolean hasPathTo(int w);
    public abstract LinkedStack<Integer> pathTo(int w);
}
