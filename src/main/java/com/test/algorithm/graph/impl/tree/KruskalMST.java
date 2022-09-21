package com.test.algorithm.graph.impl.tree;

import com.test.algorithm.graph.impl.edges.Edge;
import com.test.algorithm.graph.impl.graphs.EdgeWeightedGraph;
import com.test.algorithm.list.linked.impl.LinkedQueue;
import com.test.algorithm.tree.impl.MinPriorityQueue;
import com.test.algorithm.tree.impl.UF_Tree_Weighted;

@SuppressWarnings("all")
public class KruskalMST {
    private final UF_Tree_Weighted uf;
    private final MinPriorityQueue<Edge> pq;
    private final LinkedQueue<Edge> edges;

    public KruskalMST(EdgeWeightedGraph g) {
        this.uf = new UF_Tree_Weighted(g.V());
        this.pq = new MinPriorityQueue<>(g.E());
        this.edges = new LinkedQueue<>();

        for (var e : g.edges()) pq.insert(e);

        while (edges.size() < g.V() - 1) {
            var e = pq.delMin();
            int v = e.either(), w = e.another(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                edges.add(e);
            }
        }
    }

    public LinkedQueue<Edge> edges() {
        return edges;
    }
}
