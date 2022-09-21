package com.test.algorithm.graph.interfaces.topological;

import com.test.algorithm.list.linked.impl.LinkedStack;

public interface TopoLogicalTemplate {
    boolean isCycle();
    LinkedStack<Integer> order();
}
