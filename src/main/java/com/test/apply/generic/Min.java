package com.test.apply.generic;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Jmc
 */
public class Min {
    public static void main(String[] args) {
        int res = min(5, 3, 2, 1, 4);
        System.out.println(res);

        class Obj implements Comparable<Obj> {
            int data;

            @Override
            public int compareTo(Obj o) { return this.data - o.data; }

            public Obj(int data) { this.data = data; }

            @Override
            public String toString() { return "Obj { data: " + this.data + " }"; }
        }

        var res2 = min(new Obj(17), new Obj(-1), new Obj(4));
        System.out.println(res2);
    }

    @SafeVarargs
    static <T extends Comparable<T>> T min(T... ts) {
        return Optional.ofNullable(ts)
                .map(Arrays::stream)
                .orElseThrow()
                .min(Comparable::compareTo)
                .orElseThrow();
    }
}

