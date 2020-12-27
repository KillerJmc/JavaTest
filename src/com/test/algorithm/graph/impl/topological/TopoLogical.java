package com.test.algorithm.graph.impl.topological;

import com.test.algorithm.graph.impl.DiGraph;
import com.test.algorithm.graph.interfaces.topological.TopoLogicalTemplate;
import com.test.algorithm.list.linked.impl.LinkedStack;

public class TopoLogical implements TopoLogicalTemplate {
    private final LinkedStack<Integer> order;

    public TopoLogical(DiGraph g) {
        this.order = new DirectedCycle(g).hasCycle() ? null : new DepthFirstOrder(g).reversePost();
    }

    @Override
    public boolean isCycle() {
        return order == null;
    }

    @Override
    public LinkedStack<Integer> order() {
        return order;
    }
}
