package com.test.algorithm.graph.interfaces.topological;

import com.test.algorithm.graph.impl.graphs.DiGraph;
import com.test.algorithm.list.linked.impl.LinkedStack;

public abstract class DepthFirstOrderTemplate {
    protected abstract void dfs(DiGraph g, int v);
    public abstract LinkedStack<Integer> reversePost();
}
