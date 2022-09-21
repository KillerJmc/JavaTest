package com.test.algorithm.test.list.linked;

import com.jmc.lang.reflect.Reflects;
import com.jmc.math.exp.ExactExp;
import com.test.algorithm.list.linked.impl.LinkedStack;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static com.jmc.lang.Objs.orEquals;
import static com.jmc.lang.Outs.newLine;

public class LinkedStackTest {
    @Test
    public void test() {
        var s = new LinkedStack<String>();
        s.push("Hello");
        s.push("666");
        s.push("OKSir");

        for (var str : s) System.out.println(str);
        newLine();
        while (!s.isEmpty()) System.out.println(s.pop());
        System.out.println(s.size());
    }

    public boolean isMatch(String str) {
        var s1 = new LinkedStack<Character>();
        var s2 = new LinkedStack<Character>();

        for (var c : str.toCharArray())
            if (orEquals(c, '(', ')', '[', ']', '{', '}')) s1.push(c);
        s2.push(s1.pop());

        while (s1.size() > 0) {
            var left = s1.pop();
            if (orEquals(left, ')', ']', '}')) {
                s2.push(left);
                continue;
            }

            var right = s2.pop();
            if (right == null) return false;

            switch (right) {
                case ')' -> { if (!left.equals('(')) return false; }
                case ']' -> { if (!left.equals('[')) return false; }
                case '}' -> { if (!left.equals('{')) return false; }
            }
        }
        return s1.size() == s2.size();
    }
    
    @Test
    public void test2() {
        String[] a = {"(上海{长安[]})", "()", "()[]{}", "(]", "([)]", "{[]}", "((((((((((((", ")))))))))))))"};
        Boolean[] result = {true, true, true, false, false, true, false, false};
        for (int i = 0; i < a.length; i++) {
            var flag = isMatch(a[i]);
            if (flag != result[i]) {
                System.err.println("Wrong answer!");
                return;
            }
        }
        System.out.println("Admitted!");
    }

    @SuppressWarnings("all")
    private String[] transToSuffixExp(String exp) throws Exception {
        Method split = Reflects.getStaticMethod(ExactExp.class, "split", String.class);
        var infixExp = split.invoke(null, exp);
        Method transToSuffixExp = Reflects.getStaticMethod(ExactExp.class, "transToSuffixExp", String[].class);
        return (String[]) transToSuffixExp.invoke(null, infixExp);
    }

    @Test
    public void test3() throws Exception {
        var suffixExp = transToSuffixExp("3 * (17 - 15) + 18 / 6");
        System.out.println(Arrays.toString(suffixExp));

        var s = new LinkedStack<String>();
        for (var t : suffixExp) {
            if (Character.isDigit(t.charAt(0))) {
                s.push(t);
            } else {
                int b = Integer.parseInt(s.pop());
                int a = Integer.parseInt(s.pop());
                s.push(switch (t) {
                    case "+" -> a + b + "";
                    case "-" -> a - b + "";
                    case "*" -> a * b + "";
                    case "/" -> a / b + "";
                    default -> throw new IllegalStateException("Unexpected value: " + t);
                });
            }
        }

        if ((3 * (17 - 15) + 18 / 6) == Integer.parseInt(s.pop())) {
            System.out.println("Admitted!");
        } else {
            System.err.println("Wrong answer!");
        }
    }
}
