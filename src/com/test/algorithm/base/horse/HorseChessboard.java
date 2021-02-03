package com.test.algorithm.base.horse;

import com.jmc.lang.extend.Bools;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class HorseChessboard {
    private final int X;
    private final int Y;

    private final int[][] chessboard;
    private final boolean[] visited;
    private boolean finished;

    public HorseChessboard(int[][] chessboard) {
        this.chessboard = chessboard;
        this.X = chessboard.length;
        this.Y = chessboard[0].length;
        this.visited = new boolean[X * Y];
        this.finished = false;
    }

    public int[][] getChessboard() {
        return chessboard;
    }

    /**
     * 计算马还能走那些位置
     * @param curPoint 现在的坐标
     * @return 可走的位置
     */
    private ArrayList<Point> next(Point curPoint) {
        var ps = new ArrayList<Point>();
        int x = curPoint.x, y = curPoint.y;
        int[][] a = {
            {x + 2, y + 1}, {x + 2, y - 1}, {x + 1, y - 2}, {x - 1, y - 2},
            {x - 2, y - 1}, {x - 2, y + 1}, {x - 1, y + 2}, {x + 1, y + 2}
        };

        for (var t : a)
            if (Bools.in(t[0], 0, X - 1) && Bools.in(t[1], 0, Y - 1))
                ps.add(new Point(t[0], t[1]));
        return ps;
    }

    public void solve(int r, int c, int step) {
        chessboard[r][c] = step;
        visited[r * Y + c] = true;
        var ps = next(new Point(r, c));

        // 用贪心算法减少回溯（使下一步的下一步选择更少）
        ps.sort(Comparator.comparingInt(p -> next(p).size()));

        for (var p : ps) {
            if (!visited[p.x * Y + p.y]) {
                solve(p.x, p.y, step + 1);
            }
        }

        if (!finished) {
            if (step == X * Y) {
                finished = true;
            } else {
                chessboard[r][c] = 0;
                visited[r * Y + c] = false;
            }
        }
    }
}
