package com.test.apply.spring.test.util;

import com.jmc.lang.Tries;
import com.test.apply.spring.aop.ProxyFactory;

/**
 * 事务Aop类
 * @author Jmc
 */
public class TransactionAop {
    /**
     * 获取事务回滚增强的代理对象
     */
    public static <T> T getProxyWithRollbackEnhancement(T t, String enhancedMethodName) {
        return ProxyFactory.getProxy(t, (method, args) -> {
            if (method.getName().equals(enhancedMethodName)) {
                Object res = null;
                try {
                    res = method.invoke(t, args);
                    // 提交事务
                    UserDatabase.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    // 回滚事务
                    UserDatabase.rollback();
                }
                return res;
            }

            return Tries.tryReturnsT(() -> method.invoke(t, args));
        });
    }
}
