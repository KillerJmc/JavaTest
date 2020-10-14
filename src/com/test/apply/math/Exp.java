package com.test.apply.math;

import java.util.*;

public class Exp {
    public static void main(String[] args) {
        double result = Exp.getResult("100 * (2 + 12) - (20 / 3) * 2");
        System.out.println("表达式计算结果：" + result);
        System.out.println("正确结果：" + (100 * (2 + 12) - (20.0 / 3) * 2));
    }

    private static final Map<String, Integer> priority = Map.of(
        "+", 1,
        "-", 1,
        "*", 2,
        "/", 2,
        "(", 0,
        ")", 0,
        "#", 0
    );

    public static String[] split(String exp) {
        char[] cs = exp.replace(" ", "").toCharArray();
        List<String> list = new ArrayList<>();
        StringBuilder t = new StringBuilder();

        for (char c : cs) {
            if (c < '0' || c > '9') {
                if (t.length() > 0) {
                    list.add(t.toString());
                    t.delete(0, t.length());
                }
                list.add(c + "");
                continue;
            }
            t.append(c);
        }
        list.add(t.toString());
        list.add("#");

        return list.toArray(new String[0]);
    }

    public static String[] transToSuffixExp(String[] infixExp) {
        Stack<String> s1 = new Stack<>();
        s1.push("#");
        Stack<String> s2 = new Stack<>();

        for (String s : infixExp) {
            if (Character.isDigit(s.charAt(0))) {
                s2.push(s);
                continue;
            }

            if (priority.get(s) > 0) {
                while (priority.get(s) <= priority.get(s1.peek())) {
                    s2.push(s1.pop());
                }
                s1.push(s);
                continue;
            }

            if (s.equals("(")) {
                s1.push(s);
                continue;
            }

            if (s.equals(")")) {
                while (!s1.peek().equals("(")) {
                    s2.push(s1.pop());
                }
                s1.pop();
                continue;
            }

            if (s.equals("#")) {
                while (s1.size() > 1) {
                    s2.push(s1.pop());
                }
            }
        }
        return s2.toArray(new String[0]);
    }

    public static double calculate(String[] suffixExp) {
        Stack<Double> stack = new Stack<>();

        for (String s : suffixExp) {
            if (Character.isDigit(s.charAt(0))) {
                stack.push(Double.valueOf(s));
                continue;
            }

            if (priority.get(s) > 0) {
                double latter = stack.pop();
                double former = stack.pop();

                double result = switch(s) {
                    case "+" -> former + latter;
                    case "-" -> former - latter;
                    case "*" -> former * latter;
                    case "/" -> former / latter;
                    default -> throw new IllegalStateException("Unexpected value: " + s);
                };
                stack.push(result);
            }
        }
        return stack.pop();
    }

    public static double getResult(String exp) {
        String[] infixExp = split(exp);
        String[] suffixExp = transToSuffixExp(infixExp);
        return calculate(suffixExp);
    }
}
