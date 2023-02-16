package com.test.apply.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ClassAnnoAspect {
    @Around("@within(Demo)")
    public Object classAnno(ProceedingJoinPoint joinPoint) {
        System.err.println(joinPoint.getSignature());
        return null;
    }

    @Demo
    private static class What {
        public static void m1() {}
        public static void m2() {}
    }

    public static void main(String[] args) {
        What.m1();
        What.m2();
    }
}
