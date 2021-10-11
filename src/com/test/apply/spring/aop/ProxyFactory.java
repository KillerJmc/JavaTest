package com.test.apply.spring.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * 代理对象生成类
 */
public class ProxyFactory {
    /**
     * 获取代理对象（JDK Proxy）
     * @param t 被代理对象
     * @param enhanceBlock 增强块
     * @param <T> 被代理对象的类型
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProxy(T t, BiFunction<Method, Object[], Object> enhanceBlock) {
        Class<?> c = t.getClass();
        return (T) Proxy.newProxyInstance(c.getClassLoader(), c.getInterfaces(),
                (proxy, method, args) -> enhanceBlock.apply(method, args));
    }
}
