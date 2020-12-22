package com.test.algorithm.tree;

public interface UFTemplate {
    int count();
    boolean connected(int p, int q);
    int find(int p);
    void union(int p, int q);
}
