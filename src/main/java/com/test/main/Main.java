package com.test.main;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

class UserService {
    public String getPassword(String name) {
        if ("Jmc".equals(name)) {
            return "123456";
        }
        return null;
    }
}

/**
 * @author Jmc
 */
public class Main {
    @Aspect
    public static class MyAspect {
        @Around("execution(* com.test.main.UserService.*(..))")
        public Object getDamn(ProceedingJoinPoint joinPoint) throws Throwable {
            var res = joinPoint.proceed();
            return res + "???shiton";
        }
    }

    public static void main(String... args) {
        var userService = new UserService();
        System.out.println(userService.getPassword("Jmc"));
    }
}

