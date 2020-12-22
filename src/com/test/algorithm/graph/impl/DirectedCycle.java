package com.test.algorithm.graph.impl;

import com.test.algorithm.graph.DirectedCycleTemplate;

public class DirectedCycle extends DirectedCycleTemplate {
    private boolean hasCycle;
    private final boolean[] onStack;
    private final boolean[] marked;

    public DirectedCycle(DiGraph g) {
        this.hasCycle = false;
        this.onStack = new boolean[g.V()];
        this.marked = new boolean[g.V()];

        for (int i = 0; i < g.V() && !hasCycle; i++) {
            if (!marked[i]) dfs(g, i);
        }
    }

    @Override
    protected void dfs(DiGraph g, int v) {
        onStack[v] = marked[v] = true;
        for (var t : g.adj(v)) {
            if (!marked[t]) dfs(g, t);
            if (onStack[t]) {
                hasCycle = true;
                return;
            }
        }
        onStack[v] = false;
    }

    @Override
    public boolean hasCycle() {
        return hasCycle;
    }
}
