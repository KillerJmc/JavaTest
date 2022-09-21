package com.test.algorithm.graph.impl.tree;

import com.test.algorithm.graph.impl.edges.Edge;
import com.test.algorithm.graph.impl.graphs.EdgeWeightedGraph;
import com.test.algorithm.tree.impl.IndexMinPriorityQueue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PrimMST {
    private final boolean[] marked;
    private final Edge[] edgeTo;
    private final double[] distTo;
    private final IndexMinPriorityQueue<Double> pq;

    public PrimMST(EdgeWeightedGraph g) {
        this.marked = new boolean[g.V()];
        this.edgeTo = new Edge[g.V()];
        this.distTo = new double[g.V()];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        this.pq = new IndexMinPriorityQueue<>(g.V());

        pq.insert(0, 0.0);
        while (!pq.isEmpty()) visit(g, pq.delMin());
    }

    protected void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (var e : g.adj(v)) {
            var w = e.another(v);
            if (marked[w]) continue;

            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                pq.insert(w, e.weight());
            }
        }
    }

    public List<Edge> edges() {
        return Arrays.stream(edgeTo).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
