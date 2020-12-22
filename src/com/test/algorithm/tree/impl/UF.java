package com.test.algorithm.tree.impl;

import com.test.algorithm.tree.UFTemplate;

public class UF implements UFTemplate {
    protected final int[] eleAndGroup;
    protected int count;

    public UF(int N) {
        this.count = N;
        this.eleAndGroup = new int[N];
        // 索引是元素，分组是其对应的索引的值
        for (int i = 0; i < eleAndGroup.length; i++) eleAndGroup[i] = i;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int find(int p) {
        return eleAndGroup[p];
    }

    @Override
    public void union(int p, int q) {
        if (connected(p, q)) return;

        int pGroup = find(p), qGroup = find(q);

        for (int i = 0; i < eleAndGroup.length; i++)
            if (eleAndGroup[i] == pGroup) eleAndGroup[i] = qGroup;

        count--;
    }
}
