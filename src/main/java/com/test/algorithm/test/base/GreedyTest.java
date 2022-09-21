package com.test.algorithm.test.base;

import com.test.algorithm.base.greedy.Greedy.Greedy;
import org.junit.Test;

import java.util.Arrays;

public class GreedyTest {
    @Test
    public void test() {
        String[][] areas = {{"北京", "上海", "天津"}, {"广州", "北京", "深圳"}, {"成都", "上海", "杭州"},
                {"上海", "天津"}, {"杭州", "大连"}};
        int[] result = Greedy.solve(areas);
        System.out.println(Arrays.toString(result));
    }
}