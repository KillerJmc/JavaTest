package com.test.algorithm.test.graph;

import com.test.algorithm.graph.impl.path.Floyd;
import org.junit.Test;

public class FloydTest {
    @Test
    public void test() {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 不可联通点距离
        int N = 999999;
        /*
                A -——5——- B
              /  \      /  \
             7    2   3     9
            /      \ /       \
           C        G        D
           \       / \      /
            8     4   6    4
             \   /    \   /
              E -——5——- F
         */
        int[][] dis = {
            {0, 5, 7, N, N, N, 2},
            {5, 0, N, 9, N, N, 3},
            {7, N, 0, N, 8, N, N},
            {N, 9, N, 0, N, 4, N},
            {N, N, 8, N, 0, 5, 4},
            {N, N, N, 4, 5, 0, 6},
            {2, 3, N, N, 4, 6, 0}
        };

        var g = new Floyd(vertex, dis);
        g.solve();
        g.show();
    }
}