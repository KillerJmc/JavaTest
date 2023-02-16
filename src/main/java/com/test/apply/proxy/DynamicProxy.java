package com.test.apply.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * 基于CGLib的动态代理 <br>
 * VM Options: --add-opens java.base/java.lang=ALL-UNNAMED
 * @author Jmc
 */
public class DynamicProxy {
    /**
     * 获取动态代理实例
     * @param targetClass 自身的Class对象
     * @param methodCallBack 方法钩子
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> targetClass, MethodInterceptor methodCallBack) {
        return (T) new Enhancer() {{
            setSuperclass(targetClass);
            setCallback(methodCallBack);
        }}.create();
    }
}
