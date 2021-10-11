package com.test.apply.spring.ioc.core;

/**
 * 工厂类，生产对象
 * @author Jmc
 */
public interface BeanFactory {
    /**
     * 通过bean的id获取对象
     * @param beanId bean的id
     * @return bean对象
     */
    Object getBean(String beanId);

    /**
     * 通过bean的类型获取对象
     * @param beanType bean的Class对象
     * @param <T> bean的类型
     * @return bean对象
     */
    <T> T getBean(Class<T> beanType);
}
