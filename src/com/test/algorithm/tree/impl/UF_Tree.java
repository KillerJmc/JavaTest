package com.test.algorithm.tree.impl;

public class UF_Tree extends UF {
    public UF_Tree(int N) {
        super(N);
    }

    @Override
    public int find(int p) {
        while (true) {
            if (p == eleAndGroup[p]) return p;
            p = eleAndGroup[p];
        }
    }

    @Override
    public void union(int p, int q) {
        var pRoot = find(p);
        var qRoot = find(q);
        if (pRoot == qRoot) return;

        eleAndGroup[pRoot] = qRoot;

        count--;
    }
}
