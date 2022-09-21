package com.test.algorithm.test.base;

import com.test.algorithm.base.string.KMP;
import com.test.algorithm.base.string.ViolenceMatch;
import org.junit.Test;

import java.util.Arrays;

public class StringTest {
    @Test
    public void test1() {
        String s1 = "bbc abcdab abcdabcdabde", s2 = "abcdabd";
        int i = ViolenceMatch.match(s1, s2);
        System.out.println(i);
    }

    @Test
    public void test2() {
        System.out.println(Arrays.toString(KMP.getNext("ABABD")));
        String s1 = "bbc abcdab abcdabcdabde", s2 = "abcdabd";
        int i = KMP.match(s1, s2);
        System.out.println(i);
    }
}