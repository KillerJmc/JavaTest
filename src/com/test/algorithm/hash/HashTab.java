package com.test.algorithm.hash;

import com.jmc.array.Arrs;

public class HashTab<T> {
    private final HashTabLikedList<T>[] lists;
    private final int size;

    @SuppressWarnings("unchecked")
    public HashTab(int size) {
        this.lists = Arrs.newInstance(HashTabLikedList.class, size);
        this.size = size;
    }

    public void add(int id, T t) {
        lists[hash(id)].insert(t);
    }

    public T findById(int id) {
        return lists[hash(id)].findById(id);
    }

    public void deleteById(int id) {
        lists[hash(id)].deleteById(id);
    }

    public int hash(int id) {
        return id % size;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder("HashTab[\n");
        for (var l : lists) sb.append("\t").append(l).append(", \n");
        sb.delete(sb.length() - 3, sb.length()).append("\n]");
        return sb.toString();
    }
}
