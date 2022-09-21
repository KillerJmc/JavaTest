package com.test.algorithm.test.list.linked;

import com.test.algorithm.list.linked.impl.LinkedSortedSymbolTable;
import org.junit.Test;

import static com.jmc.lang.Outs.newLine;

public class LinkedSortedSymbolTableTest {
    @Test
    public void test() {
        var table = new LinkedSortedSymbolTable<Integer, String>();
        table.put(3, "Lucy");
        table.put(1, "Jmc");
        table.put(2, "Jerry");
        newLine(() -> table.forEach((k, v) -> System.out.printf("k -> %d v -> %s\n", k, v)));
    }
}
