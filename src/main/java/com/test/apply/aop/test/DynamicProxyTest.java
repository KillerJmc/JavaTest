package com.test.apply.aop.test;

import com.test.apply.aop.DynamicProxy;

import java.util.Base64;

/**
 * @author Jmc
 */
public class DynamicProxyTest {
    public static class UserService {
        public String getPassword(String name) {
            if ("Jmc".equals(name)) {
                return "123456";
            }
            return null;
        }
    }

    public static void main(String[] as) throws Exception {
        // 获取动态代理类
        var userService = DynamicProxy.getInstance(UserService.class, (obj, method, args, proxy) -> {
            // 调用父方法（cglib是生成子类，要调用原方法就相当于需要调用父方法）
            var password = (String) proxy.invokeSuper(obj, args);

            // 对密码进行加密
            return Base64.getEncoder().encodeToString(password.getBytes());
        });

        var password = userService.getPassword("Jmc");
        System.out.println("password -> " + password);
    }
}
