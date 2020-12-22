package com.test.algorithm.test.tree;

import com.test.algorithm.tree.impl.UF_Tree_Weighted;
import org.junit.Test;

import java.util.List;
import java.util.Scanner;

public class UFTest {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        var uf = new UF_Tree_Weighted(4);
        System.out.println("默认情况下并查集中有" + uf.count() + "个分组");

        while (true) {
            System.out.print("\n请输入第一个要合并的元素: ");
            int p = in.nextInt();
            System.out.print("\n请输入第二个要合并的元素: ");
            int q = in.nextInt();

            if (uf.connected(p, q)) {
                System.out.printf("\n元素%d和元素%d已经在同一组了\n", p, q);
            } else {
                uf.union(p, q);
                System.out.printf("\n当前并查集中还有%d个分组\n", uf.count());
            }
        }
    }

    // 解决畅通工程
    @Test
    public void test() {
        int cities = 20;
        var uf = new UF_Tree_Weighted(cities);
        var builtRoads = List.of(
            List.of(0, 1),
            List.of(6, 9),
            List.of(3, 8),
            List.of(5, 11),
            List.of(2, 12),
            List.of(6, 10),
            List.of(4, 8)
        );

        for (var l : builtRoads) uf.union(l.get(0), l.get(1));

        var needToBuild = uf.count() - 1;
        System.out.printf("还需要修建%d条道路，才能实现畅通工程\n", needToBuild);
    }
}
