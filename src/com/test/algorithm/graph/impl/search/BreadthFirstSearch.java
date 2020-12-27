package com.test.algorithm.graph.impl.search;

import com.test.algorithm.graph.impl.Graph;
import com.test.algorithm.graph.interfaces.search.BreadthFirstSearchTemplate;
import com.test.algorithm.list.sequence.impl.SequenceQueue;

public class BreadthFirstSearch extends BreadthFirstSearchTemplate {
    private final boolean[] marked;
    private final SequenceQueue<Integer> awaitQueue;
    private int count;

    public BreadthFirstSearch(Graph g, int v) {
        this.marked = new boolean[g.V()];
        this.awaitQueue = new SequenceQueue<>();
        this.count = 0;
        bfs(g, v);
    }

    @Override
    protected void bfs(Graph g, int v) {
        awaitQueue.add(v);
        marked[v] = true;
        count++;

        while (!awaitQueue.isEmpty()) {
            var ver = awaitQueue.poll();
            for (var t : g.adj(ver)) {
                if (!marked(t)) {
                    awaitQueue.add(t);
                    marked[t] = true;
                    count++;
                }
            }
        }

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
