package com.test.algorithm.test.list.sequence;

import com.test.algorithm.list.sequence.impl.SequenceQueue;
import org.junit.Test;

import static com.jmc.lang.extend.Outs.newLine;

public class SequenceQueueTest {
    @Test
    public void test() {
        var queue = new SequenceQueue<String>(1);
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
