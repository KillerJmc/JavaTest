package com.test.algorithm.graph.interfaces;

import com.test.algorithm.list.linked.impl.LinkedQueue;

public interface GraphTemplate {
    int V();
    int E();
    void addEdge(int v1, int v2);
    LinkedQueue<Integer> adj(int v);
}
