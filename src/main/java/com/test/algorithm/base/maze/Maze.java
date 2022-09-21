package com.test.algorithm.base.maze;

import java.util.LinkedList;
import java.util.Scanner;

public class Maze {
    public static void main(String[] args) {

    }

    Scanner in = new Scanner(System.in);
    public void solveByBfs() {
        int X = 4, Y = 6;
        int[][] map = new int[X][Y];
        for (int i = 0; i < X; i++) {
            char[] cs = in.next().toCharArray();
            for (int j = 0; j < Y; j++) {
                map[i][j] = cs[j] - '0';
            }
        }

        boolean[][] marked = new boolean[X][Y];
        char[] dir = {'D', 'L', 'R', 'U'};
        int[][] step = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};

        bfs(0, 0, map, marked, dir, step);
    }

    private void bfs(int x, int y, int[][] map, boolean[][] marked, char[] dir, int[][] step) {
        record Point(int x, int y, String path) {}
        var queue = new LinkedList<Point>();
        queue.add(new Point(x, y, ""));

        while (!queue.isEmpty()) {
            var p = queue.poll();
            marked[p.x][p.y] = true;

            for (int i = 0; i < dir.length; i++) {
                int newX = p.x + step[i][0];
                int newY = p.y + step[i][1];
                boolean flag = newX >= 0 && newX < map.length && newY >= 0 && newY < map[0].length
                        && !marked[newX][newY] && map[newX][newY] != 1;

                if (flag) {
                    var res = new Point(newX, newY, p.path + dir[i]);
                    if (newX == map.length - 1 && newY == map[0].length - 1) {
                        System.out.println("最少步数是" + res.path.length());
                        System.out.println("最短路径是" + res.path);
                        return;
                    }
                    queue.add(res);
                }
            }
        }
    }

    public void solveByDfs() {
        int X = 4, Y = 6;
        int[][] map = new int[X][Y];
        for (int i = 0; i < X; i++) {
            char[] cs = in.next().toCharArray();
            for (int j = 0; j < Y; j++) {
                map[i][j] = cs[j] - '0';
            }
        }
        boolean[][] marked = new boolean[X][Y];
        char[] dir = {'D', 'L', 'R', 'U'};
        int[][] step = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};

        var res = new StringBuilder();
        marked[0][0] = true;

        dfs(res, "", 0, 0, map, marked, dir, step);

        System.out.println("最少步数是" + res.length());
        System.out.println("最短路径是" + res);
    }

    private void dfs(StringBuilder res, String s, int x, int y, int[][] map, boolean[][] marked, char[] dir, int[][] step) {
        if (x == map.length - 1 && y == map[0].length - 1) {
            if (res.length() == 0 || s.length() < res.length()) {
                res.delete(0, res.length());
                res.append(s);
                return;
            }
        }

        for (int i = 0; i < dir.length; i++) {
            int newX = x + step[i][0];
            int newY = y + step[i][1];
            boolean flag = newX >= 0 && newX < map.length && newY >= 0 && newY < map[0].length
                    && !marked[newX][newY] && map[newX][newY] != 1;
            if (flag) {
                marked[newX][newY] = true;
                dfs(res, s + dir[i], newX, newY, map, marked, dir, step);
                marked[newX][newY] = false;
            }
        }
    }
}
