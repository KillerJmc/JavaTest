package com.test.apply.aop.cglib.test;

import com.test.apply.aop.cglib.ArgAnnoAop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jmc
 */
public class ArgAnnoAopTest {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    private @interface Demo {
        String value();
    }

    @SuppressWarnings("unused")
    private static class Test {
        public Test() {}
        public void m1(Long a, Long b, Long c) {}
        public void m2(@Demo("Here") Long a, Long b, Long c) {}
    }

    @SuppressWarnings("all")
    public static void main(String[] a) {
        var test = ArgAnnoAop.getInstance(Test.class, Demo.class, (paramToAnno, method, args) -> {
            paramToAnno.forEach((param, anno) -> {
                System.out.printf("param -> %s, anno -> %s\n", param, anno);
            });
            return method.apply(args);
        });

        System.out.println("m1: ");
        test.m1(null, null, null);
        System.out.println("m2: ");
        test.m2(null, null, null);
    }
}
