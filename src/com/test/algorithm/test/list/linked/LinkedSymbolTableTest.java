package com.test.algorithm.test.list.linked;

import com.test.algorithm.list.linked.impl.LinkedSymbolTable;
import org.junit.Test;

import static com.jmc.lang.extend.Outs.newLine;

public class LinkedSymbolTableTest {
    @Test
    public void test() {
        var table = new LinkedSymbolTable<String, Integer>();
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
