package com.test.algorithm.test.list.sequence;

import com.test.algorithm.list.sequence.impl.SequenceSortedSymbolTable;
import org.junit.Test;

import static com.test.main.Tools.newLine;

public class SequenceSortedSymbolTableTest {
    @Test
    public void test() {
        var table = new SequenceSortedSymbolTable<Integer, String>();
        table.put(3, "Lucy");
        table.put(1, "Jmc");
        table.put(2, "Jerry");
        newLine(() -> table.forEach((k, v) -> System.out.printf("k -> %d v -> %s\n", k, v)));
    }
}
