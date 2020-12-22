package com.test.algorithm.graph.impl;

import com.test.algorithm.graph.DepthFirstSearchTemplate;

public class DepthFirstSearch extends DepthFirstSearchTemplate {
    private final boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph g, int v) {
        this.marked = new boolean[g.V()];
        this.count = 0;
        dfs(g, v);
    }

    @Override
    protected void dfs(Graph g, int v) {
        marked[v] = true;
        count++;
        for (var w : g.adj(v))
            if (!marked(w)) dfs(g, w);
    }

    @Override
    public boolean marked(int w) {
        return marked[w];
    }

    @Override
    public int count() {
        return count;
    }
}
