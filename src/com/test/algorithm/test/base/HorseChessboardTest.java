package com.test.algorithm.test.base;

import com.jmc.util.Timers;
import com.test.algorithm.base.horse.HorseChessboard;
import com.test.algorithm.base.util.MyArr;
import org.junit.Test;

public class HorseChessboardTest {
    @Test
    public void test() {
        var horse = new HorseChessboard(new int[8][8]);
        Timers.milliTimer(() -> horse.solve(1, 3, 1));

        System.out.println(MyArr.toString(horse.getChessboard(), true));
    }
}