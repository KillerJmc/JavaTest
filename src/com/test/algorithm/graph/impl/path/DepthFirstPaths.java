package com.test.algorithm.graph.impl.path;

import com.test.algorithm.graph.impl.graphs.Graph;
import com.test.algorithm.graph.interfaces.path.DepthFirstPathsTemplate;
import com.test.algorithm.list.linked.impl.LinkedStack;

public class DepthFirstPaths extends DepthFirstPathsTemplate {
    private final boolean[] marked;
    private final int v;
    private final int[] edgeTo;

    public DepthFirstPaths(Graph g, int v) {
        this.marked = new boolean[g.V()];
        this.v = v;
        this.edgeTo = new int[g.V()];
        dfs(g, v);
    }

    @Override
    protected void dfs(Graph g, int v) {
        marked[v] = true;

        for (var t : g.adj(v)) {
            if (!marked[t]) {
                // 到达顶点t路径的最后一个顶点是v
                edgeTo[t] = v;
                dfs(g, t);
            }
        }
    }

    @Override
    public boolean hasPathTo(int w) {
        return marked[w];
    }

    @Override
    public LinkedStack<Integer> pathTo(int w) {
        if (!hasPathTo(w)) return null;

        var s = new LinkedStack<Integer>();
        for (; w != v; w = edgeTo[w])
            s.push(w);

        s.push(v);
        return s;
    }
}
