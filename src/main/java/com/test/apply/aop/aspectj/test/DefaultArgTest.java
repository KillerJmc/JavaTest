package com.test.apply.aop.aspectj.test;

import com.test.apply.aop.aspectj.util.DefaultArg;

public class DefaultArgTest {
    public static class UserService {
        public static String getInfo(@DefaultArg("1") Integer id, @DefaultArg("Jmc") String name) {
            return "id = %d, name = %s".formatted(id, name);
        }
    }

    public static void main(String[] args) {
        // 此时会注入默认参数
        var info = UserService.getInfo(null, null);
        System.out.println(info);

        info = UserService.getInfo(2, null);
        System.out.println(info);

        info = UserService.getInfo(null, "Lucy");
        System.out.println(info);

        info = UserService.getInfo(3, "Jack");
        System.out.println(info);
    }
}
