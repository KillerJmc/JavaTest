package com.test.Annotation.practice;

import java.lang.reflect.Method;

/**
 * Annotation
 * 注解可以免去properties文件书写
 */
@Pro(className = "com.test.Annotation.practice.Animal1", methodName = "eat")
public class ReflectTest {
    public static void main(String[] args) throws Exception {
        //解析注解
        var c = ReflectTest.class;
        //生成了子类实现对象（如下）
        Pro pro = c.getAnnotation(Pro.class);
        var className = pro.className();
        var methodName = pro.methodName();
        System.out.println(className);
        System.out.println(methodName);

        var c2 = Class.forName(className);
        Object o = c2.getDeclaredConstructor().newInstance();
        Method m = c2.getDeclaredMethod(methodName);
        m.invoke(o);
    }
}

/*
    public class ProImpl implements Pro {
        public String className() { return "com.test.Annotation.practice.Animal1"; }
        public String methodName() { return "eat"; }
    }
 */
