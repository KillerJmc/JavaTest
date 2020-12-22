package com.test.algorithm.tree.impl;

import java.util.Arrays;

public class UF_Tree_Weighted extends UF_Tree {
    // 用来存储每个根节点对应的树中保存的节点个数
    private final int[] sz;

    public UF_Tree_Weighted(int N) {
        super(N);
        this.sz = new int[N];
        Arrays.fill(sz, 1);
    }

    @Override
    public void union(int p, int q) {
        var pRoot = find(p);
        var qRoot = find(q);
        if (pRoot == qRoot) return;

        // 确保小树指向大树
        if (sz[pRoot] < sz[qRoot]) {
            eleAndGroup[pRoot] = qRoot;
            sz[qRoot] += sz[qRoot];
        } else {
            eleAndGroup[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
        count--;
    }
}
