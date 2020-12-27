package com.test.algorithm.graph.impl.weight;

import com.test.algorithm.graph.interfaces.weight.PrimMSTTemplate;
import com.test.algorithm.list.linked.impl.LinkedQueue;
import com.test.algorithm.tree.impl.IndexMinPriorityQueue;

import java.util.Arrays;

public class PrimMST extends PrimMSTTemplate {
    private final boolean[] marked;
    private final Edge[] edgeTo;
    private final double[] weightTo;
    private final IndexMinPriorityQueue<Double> pq;

    public PrimMST(EdgeWeightedGraph g) {
        this.marked = new boolean[g.V()];
        this.edgeTo = new Edge[g.V()];
        this.weightTo = new double[g.V()];
        Arrays.fill(weightTo, Double.POSITIVE_INFINITY);
        this.pq = new IndexMinPriorityQueue<>(g.V());

        pq.insert(0, 0.0);
        while (!pq.isEmpty()) visit(g, pq.delMin());
    }

    @Override
    protected void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (var e : g.adj(v)) {
            var w = e.another(v);
            if (marked[w]) continue;

            if (e.weight() < weightTo[w]) {
                edgeTo[w] = e;
                weightTo[w] = e.weight();
                pq.insert(w, e.weight());
            }
        }
    }

    @Override
    public LinkedQueue<Edge> edges() {
        var edges = new LinkedQueue<Edge>();
        for (var t : edgeTo) if (t != null) edges.add(t);
        return edges;
    }
}
