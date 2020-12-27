package com.test.algorithm.test.list.linked;

import com.test.algorithm.list.linked.impl.LinkedQueue;
import org.junit.Test;

import static com.jmc.lang.extend.Outs.newLine;

public class LinkedQueueTest {
    @Test
    public void test() {
        var queue = new LinkedQueue<String>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.add("d");

        for (var s : queue) {
            System.out.println(s);
        }

        newLine();

        while (queue.size() != 0) {
            System.out.println(queue.poll());
        }
    }
}
