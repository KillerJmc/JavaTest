package com.test.algorithm.graph.interfaces;

import com.test.algorithm.list.sequence.impl.SequenceQueue;

public abstract class DiGraphTemplate {
    public abstract int V();
    public abstract int E();
    public abstract void addEdge(int v, int w);
    public abstract SequenceQueue<Integer> adj(int v);
    protected abstract DiGraphTemplate reverse();
}
