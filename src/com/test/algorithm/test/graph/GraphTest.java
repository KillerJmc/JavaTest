package com.test.algorithm.test.graph;

import com.test.algorithm.graph.impl.*;
import com.test.algorithm.graph.impl.path.DepthFirstPaths;
import com.test.algorithm.graph.impl.path.DijkstraSP;
import com.test.algorithm.graph.impl.search.BreadthFirstSearch;
import com.test.algorithm.graph.impl.search.DepthFirstSearch;
import com.test.algorithm.graph.impl.topological.DepthFirstOrder;
import com.test.algorithm.graph.impl.topological.DirectedCycle;
import com.test.algorithm.graph.impl.topological.TopoLogical;
import com.test.algorithm.graph.impl.weight.Edge;
import com.test.algorithm.graph.impl.weight.EdgeWeightedGraph;
import com.test.algorithm.graph.impl.weight.KruskalMST;
import com.test.algorithm.graph.impl.weight.PrimMST;
import org.junit.Test;

public class GraphTest {
    private final Graph g = new Graph(13) {{
        addEdge(
                0, 5, 0, 1,
                0, 2, 0, 6,
                5, 3, 5, 4,
                3, 4, 4, 6,

                7, 8, 9, 11,
                9, 10, 9, 12,
                11, 12
        );
    }};

    @Test
    public void test() {
        var search = new DepthFirstSearch(g, 0);
        System.out.println("与起点0相通的顶点的数量（包括0）为：" + search.count());

        System.out.println("5与0" + (search.marked(5) ? "" : "不") + "相通");
        System.out.println("7与0" + (search.marked(7) ? "" : "不") + "相通");
    }

    @Test
    public void test2() {
        var search = new BreadthFirstSearch(g, 0);
        System.out.println("与起点0相通的顶点的数量（包括0）为：" + search.count());

        System.out.println("5与0" + (search.marked(5) ? "" : "不") + "相通");
        System.out.println("7与0" + (search.marked(7) ? "" : "不") + "相通");
    }

    // 畅通工程
    @Test
    public void test3() {
        int cities = 20;
        Graph g = new Graph(cities) {{
            addEdge(
                0, 1, 6, 9,
                    3, 8, 5, 11,
                    2, 12, 6, 10,
                    4, 8
            );
        }};

        var search = new BreadthFirstSearch(g, 9);

        System.out.println("9与10" + (search.marked(10) ? "" : "不") + "相通");
        System.out.println("9与8" + (search.marked(8) ? "" : "不") + "相通");
    }

    @Test
    public void test4() {
        Graph g = new Graph(6) {{
            addEdge(
                0, 2, 0, 1,
                    2, 1, 2, 3,
                    2, 4, 3, 5,
                    3, 4, 0, 5
            );
        }};

        var findPath = new DepthFirstPaths(g, 0);
        if (findPath.hasPathTo(4)) {
            System.out.println(findPath.pathTo(4));
        }
    }

    @Test
    public void test5() {
        var g = new DiGraph(6) {{
            addEdge(
            3, 0,
                0, 2,
                2, 4,
                1, 4,
                1, 0
            );
        }};

        var directedCycle = new DirectedCycle(g);
        System.out.println(directedCycle.hasCycle());
    }

    @Test
    public void test6() {
        var g = new DiGraph(6) {{
            addEdge(
            1, 3, 0, 2,
                0, 3, 2, 4,
                3, 4, 4, 5
            );
        }};
        var dfo = new DepthFirstOrder(g);
        System.out.println(dfo.reversePost());
    }

    @Test
    public void test7() {
        var g = new DiGraph(6) {{
            addEdge(
            1, 3, 0, 2,
                0, 3, 2, 4,
                3, 4, 4, 5
            );
        }};

        var topo = new TopoLogical(g);
        System.out.println(topo.isCycle() ? "有环" : "无环");
        System.out.println(topo.order());
    }


    EdgeWeightedGraph eg = new EdgeWeightedGraph(8) {{
        addEdge(
            4, 5, 0.35, 4, 7, 0.37, 5, 7, 0.28,
                0, 7, 0.16, 1, 5, 0.32, 0, 4, 0.38,
                2, 3, 0.17, 1, 7, 0.19, 0, 2, 0.26,
                1, 2, 0.36, 1, 3, 0.29, 2, 7, 0.34,
                6, 2, 0.40, 3, 6, 0.52, 6, 0, 0.58,
                6, 4, 0.93
        );
    }};

    @Test
    public void test8() {
        var primMST = new PrimMST(eg);
        for (Edge e : primMST.edges()) {
            System.out.println(e);
        }
    }

    @Test
    public void test9() {
        var kruskalMST = new KruskalMST(eg);
        for (Edge e : kruskalMST.edges()) {
            System.out.println(e);
        }
    }

    @Test
    public void test10() {
        var eg2 = new EdgeWeightDiGraph(8) {{
            addEdge(
                4, 5, 0.35, 5, 4, 0.35, 4, 7, 0.37,
                    5, 7, 0.28, 7, 5, 0.28, 5, 1, 0.32,
                    0, 4, 0.38, 0, 2, 0.26, 7, 3, 0.39,
                    1, 3, 0.29, 2, 7, 0.34, 6, 2, 0.40,
                    3, 6, 0.52, 6, 0, 0.58, 6, 4, 0.93
            );
        }};

        var dijkstraSP = new DijkstraSP(eg2, 0);
        for (var e : dijkstraSP.pathTo(6)) {
            System.out.println(e);
        }
    }
}
