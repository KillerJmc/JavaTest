package com.test.algorithm.test.list.sequence;

import com.test.algorithm.list.sequence.impl.SequenceStack;
import org.junit.Test;

import static com.jmc.lang.Outs.newLine;

public class SequenceStackTest {
    @Test
    public void test() {
        var s = new SequenceStack<String>(1);
        s.push("Hello");
        s.push("666");
        s.push("OKSir");

        for (var str : s) System.out.println(str);
        newLine();
        while (!s.isEmpty()) System.out.println(s.pop());
        System.out.println(s.size());
    }
}
