package com.test.algorithm.test.base;

import com.test.algorithm.base.hanoi.HanoiTower;
import org.junit.Test;

public class HanoiTowerTest {
    @Test
    public void test() {
        System.out.println("移动次数为：" + HanoiTower.solve(8));
    }
}
