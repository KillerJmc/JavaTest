package com.test.algorithm.graph.impl.path;

import com.test.algorithm.graph.impl.EdgeWeightDiGraph;
import com.test.algorithm.graph.impl.weight.DirectedEdge;
import com.test.algorithm.graph.interfaces.path.DijkstraSPTemplate;
import com.test.algorithm.list.linked.impl.LinkedQueue;
import com.test.algorithm.tree.impl.IndexMinPriorityQueue;

import java.util.Arrays;


public class DijkstraSP extends DijkstraSPTemplate {
    private final DirectedEdge[] edgeTo;
    private final double[] distTo;
    private final IndexMinPriorityQueue<Double> pq;

    public DijkstraSP(EdgeWeightDiGraph g, int v) {
        this.edgeTo = new DirectedEdge[g.V()];
        this.distTo = new double[g.V()];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        this.pq = new IndexMinPriorityQueue<>(g.E());

        distTo[v] = 0;
        pq.insert(v, 0.0);
        while (!pq.isEmpty()) relax(g, pq.delMin());
    }

    @Override
    protected void relax(EdgeWeightDiGraph g, int v) {
        for (var e : g.adj(v)) {
            int w = e.to();
            if (distTo[v] + e.weight() < distTo[w]) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                pq.insert(w, distTo[w]);
            }
        }
    }

    @Override
    public double distTo(int v) {
        return distTo[v];
    }

    @Override
    public boolean hasPathTo(int v) {
        return distTo(v) != Double.POSITIVE_INFINITY;
    }

    @Override
    public LinkedQueue<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;

        var edges = new LinkedQueue<DirectedEdge>();
        DirectedEdge e;
        while ((e = edgeTo[v]) != null) {
            edges.add(e);
            v = e.from();
        }
        return edges;
    }
}
