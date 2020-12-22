package com.test.algorithm.test.graph;

import com.test.algorithm.graph.impl.*;
import com.test.algorithm.tree.impl.UF_Tree_Weighted;
import org.junit.Test;

import java.util.List;

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
}
