package com.test.junit.test;

import com.test.junit.calculator.Calculator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    /**
     * Use for applying for the resources, all transfer fns would invoke this fn before running.
     * This fn must in the Test class, and would be run whether the result is success or not.
     */
    @Before
    public void init() {
        System.out.println("init...");
    }

    /**
     * all transfer fns would invoke this fn after running.
     * This fn must in the Test class, and would be run whether the result is success or not.
     */
    @After
    public void close() {
        System.out.println("close...");
    }

    /**
     * transfer Add fn
     */
    @Test
    public void testAdd() {
        var c = new Calculator();
        int result = c.add(1, 2);
        System.out.println("testAdd...");
        //assert (Assert the result is 3)
        Assert.assertEquals(3, result);
    }

    /**
     * transfer Add fn
     */
    @Test
    public void testSub() {
        var c = new Calculator();
        int result = c.sub(1, 2);
        System.out.println("testSub...");
        //assert (Assert the result is -1)
        Assert.assertEquals(-1, result);
    }
}
