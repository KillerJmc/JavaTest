package com.test.apply.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Aspect
public class ArgAnnoAspect {
    @Around("execution(* *(.., @Demo (*), ..))")
    public Object argAnno(ProceedingJoinPoint joinPoint) {
        System.err.println(joinPoint.getSignature());
        return null;
    }

    @SuppressWarnings("unused")
    private static class Fxxk {
        public static void m1(Long a, Long b, Long c) {}
        public static void m2(@Demo Long a, Long b, Long c) {}
        public static void m3(Long a, @Demo Long b, Long c) {}
        public static void m4(Long a, Long b, @Demo Long c) {}
        public static void m5(@Demo Long a, @Demo Long b, Long c) {}
        public static void m6(Long a, @Demo Long b, @Demo Long c) {}
        public static void m7(@Demo Long a, Long b, @Demo Long c) {}
        public static void m8(@Demo Long a, @Demo Long b, @Demo Long c) {}
    }

    public static void main(String[] args) {
        Fxxk.m1(null, null, null);
        Fxxk.m2(null, null, null);
        Fxxk.m3(null, null, null);
        Fxxk.m4(null, null, null);
        Fxxk.m5(null, null, null);
        Fxxk.m6(null, null, null);
        Fxxk.m7(null, null, null);
        Fxxk.m8(null, null, null);
    }
}

