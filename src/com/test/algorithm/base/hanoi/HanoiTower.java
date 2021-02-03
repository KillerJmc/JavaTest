package com.test.algorithm.base.hanoi;

public class HanoiTower {
    public static int solve(int num) {
        return solve(num, 0, 'A', 'B', 'C');
    }

    private static int solve(int num, int count, char a, char b, char c) {
        count++;

        if (num == 1) {
            System.out.printf("把第1个盘子从%c移动到%c\n", a, c);
        } else {
            // 把上面多个盘子从A移动到B
            count = solve(num - 1, count, a, c, b);
            // 把最后一个盘子从A移动到C
            System.out.printf("把第%d个盘子从%c移动到%c\n", num, a, c);
            // 把上面多个盘子从B移动到C
            count = solve(num - 1, count, b, a, c);
        }

        return count;
    }
}
