package com.test.apply.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MethodAnnoAspect {
    @Around("@annotation(Demo)")
    public Object methodAnno(ProceedingJoinPoint joinPoint) {
        System.err.println(joinPoint.getSignature());
        return null;
    }

    private static class The {
        public static void m1() {}

        @Demo
        public static void m2() {}
    }

    public static void main(String[] args) {
        The.m1();
        The.m2();
    }
}
