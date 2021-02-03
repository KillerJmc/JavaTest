package com.test.algorithm.base.string;

public class ViolenceMatch {
    public static int match(String s, String sub) {
        char[] s1 = s.toCharArray(), s2 = sub.toCharArray();
        int s1Len = s1.length, s2Len = s2.length;

        int i = 0, j = 0;
        while (i < s1Len && j < s2Len) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                // 移动到下一位
                i = i - j + 1;
                j = 0;
            }
        }
        return j == s2Len ? i - j : -1;
    }
}
