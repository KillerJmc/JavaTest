package com.test.algorithm.base.greedy.Greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Greedy {
    /**
     * 选择最少的广播台，让所有的地区全覆盖
     * @param areas 广播台及对应的覆盖地区
     * @return 所选的广播台
     */
    public static int[] solve(String[][] areas) {
        var result = new ArrayList<Integer>();
        // 求最少覆盖所有城市的电台及其编号
        var allAreas = Arrays.stream(areas)
                                         .flatMap(Stream::of)
                                         .distinct()
                                         .collect(Collectors.toList());
        while (!allAreas.isEmpty()) {
            // 当前各电台覆盖的城市数
            long[] a = Arrays.stream(areas)
                             .mapToLong(t -> Arrays.stream(t).filter(allAreas::contains).count())
                             .toArray();
            int maxIdx = 0;

            for (int i = 1; i < a.length; i++) {
                if (a[i] > a[maxIdx]) {
                    maxIdx = i;
                }
            }

            // 当前能覆盖的最多电台的数量的电台对应的下标
            result.add(maxIdx);
            for (var t : areas[maxIdx]) allAreas.remove(t);
        }
        return result.stream()
                     .mapToInt(t -> t)
                     .toArray();
    }
}
