package com.test.apply.aop.test;

import com.test.apply.aop.MethodAnnoAop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jmc
 */
public class MethodAnnoAopTest {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private @interface LogAnno {
        String value();
    }

    public static class UserService {
        public String getPassword(String name) {
            if ("Jmc".equals(name)) {
                return "123456";
            }
            return null;
        }

        @LogAnno("获取用户名")
        public String getName() {
            return "Jmc";
        }
    }

    public static void main(String[] a) {
        var userService = MethodAnnoAop.getInstance(UserService.class, LogAnno.class, (annotation, method, args) -> {
            System.out.println("获取到注解值 -> " + annotation.value());
            return method.apply(args);
        });

        var name = userService.getName();
        System.out.println("name = " + name);

        var password = userService.getPassword(name);
        System.out.println("password = " + password);
    }
}
