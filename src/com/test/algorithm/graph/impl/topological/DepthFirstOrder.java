package com.test.algorithm.graph.impl.topological;

import com.test.algorithm.graph.impl.DiGraph;
import com.test.algorithm.graph.interfaces.topological.DepthFirstOrderTemplate;
import com.test.algorithm.list.linked.impl.LinkedStack;

public class DepthFirstOrder extends DepthFirstOrderTemplate {
    private final boolean[] marked;
    private final LinkedStack<Integer> reversePost;

    public DepthFirstOrder(DiGraph g) {
        this.marked = new boolean[g.V()];
        this.reversePost = new LinkedStack<>();
        for (int i = 0; i < g.V(); i++)
            if (!marked[i]) dfs(g, i);
    }

    @Override
    protected void dfs(DiGraph g, int v) {
        marked[v] = true;

        for (var t : g.adj(v)) {
            if (!marked[t]) {
                dfs(g, t);
            }
        }
        reversePost.push(v);
    }

    @Override
    public LinkedStack<Integer> reversePost() {
        return reversePost;
    }
}
