package com.test.algorithm.test.list.sequence;

import com.test.algorithm.list.sequence.impl.SequenceSymbolTable;
import org.junit.Test;

import static com.jmc.lang.extend.Outs.newLine;

public class SequenceSymbolTableTest {
    @Test
    public void test() {
        var table = new SequenceSymbolTable<String, Integer>();
        table.put("Jmc", 18);
        table.put("Lucy", 20);
        table.put("Jenny", 3);

        newLine(() -> table.forEach((k, v) -> System.out.printf("k -> %s v -> %d\n", k, v)));

        System.out.println(table.get("Jmc"));

        table.delete("Lucy");
        table.put("Jmc", 1);

        newLine(() -> table.forEach((k, v) -> System.out.printf("k -> %s v -> %d\n", k, v)));
        System.out.println(table.size());
    }
}
