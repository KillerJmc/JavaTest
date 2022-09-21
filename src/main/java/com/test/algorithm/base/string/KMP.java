package com.test.algorithm.base.string;

public class KMP {
    /**
     * 获取next数组。其索引为：匹配失败时所对应的模式串位置，<br>
     * 值为：匹配失败后模式串应该与原串比较的位置，刚好是模式串此位之前子串的最长公共前后缀的值
     * @param sub 子串
     * @return 结果数组
     */
    public static int[] getNext(String sub) {
        char[] s = sub.toCharArray();
        int[] next = new int[sub.length()];
        // 做标记，第0位即不匹配，算法中后移处理
        next[0] = -1;

        // i表示截取的最长字符串的最后一个字符位置和数组记录的指针，j表示最长公共前后缀的值和最长前缀的下一个字符的位置
        int i = 0, j = -1;
        // 最好状态回溯：从最长情况回溯到最短情况，保证是最长公共前后缀
        while (i < next.length - 1) {
            if (j == -1 || s[i] == s[j])
                next[++i] = ++j;
            else
                j = next[j];
        }
        return next;
    }

    /**
     * 匹配字符串
     * @param s 原串
     * @param sub 模式串
     * @return 原串中模式串对应的首索引
     */
    public static int match(String s, String sub) {
        char[] s1 = s.toCharArray(), s2 = sub.toCharArray();
        int[] next = getNext(sub);

        int i = 0, j = 0;
        while (i < s1.length && j < s2.length) {
            if (j == -1 || s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        return j == s2.length ? i - j : -1;
    }
}
