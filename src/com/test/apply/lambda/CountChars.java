package com.test.apply.lambda;

import java.util.stream.Collectors;

/**
 * @author Jmc
 */
public class CountChars {
    public static void main(String[] args) {
        var s = "abccdd";

        s.chars()
         .mapToObj(t -> (char) t)
         .collect(Collectors.groupingBy(t -> t, Collectors.counting()))
         .forEach((k, v) -> System.out.println(k + " -> " + v));
    }
}
