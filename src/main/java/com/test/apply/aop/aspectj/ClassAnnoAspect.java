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
        public What() {
            System.out.println("这行字不会被打印，表明构造方法被拦截！");
        }

        public static void m1() {}
        public static void m2() {}
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {
        new What();
        What.m1();
        What.m2();
    }
}
