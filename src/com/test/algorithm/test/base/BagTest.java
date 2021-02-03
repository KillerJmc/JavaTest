package com.test.algorithm.test.base;

import com.test.algorithm.base.bag.Bag;
import com.test.algorithm.base.bag.Bag2;
import com.test.algorithm.base.bag.Bag3;
import org.junit.Test;

public class BagTest {
    private final int[] val = {3, 4, 5, 6};
    private final int[] vol = {2, 3, 4, 5};
    private final int[] num = {1, 1, 1, 5};

    @Test
    public void test() {
        var bag = new Bag(val, vol);
        bag.solve(8);
        bag.printDp();
    }

    @Test
    public void test2() {
        var bag = new Bag2(val, vol);
        bag.solve(8);
        bag.printDp();
    }

    @Test
    public void test3() {
        var bag = new Bag3(val, vol, num);
        bag.solve(16);
        bag.printDp();
    }
}