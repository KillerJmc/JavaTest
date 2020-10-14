package com.test.annotation.practice;

import com.test.main.Tools;

import java.lang.reflect.Method;

import static com.jmc.lang.Outs.newLine;

/**
 * Annotation
 * 注解可以免去properties文件书写
 */
@Pro(className = "com.test.Annotation.practice.Animal", methodName = "eat")
public class ReflectTest {
    public static void main(String[] args) throws Exception {
        var c = ReflectTest.class;
        //生成了子类实现对象（如下）
        Pro pro = c.getAnnotation(Pro.class);
        var className = pro.className();
        var methodName = pro.methodName();
        System.out.println(className);
        System.out.println(methodName);

        newLine();

        var c2 = Class.forName(className);
        Object o = c2.getDeclaredConstructor().newInstance();
        Method m = c2.getMethod(methodName);
        m.invoke(o);
    }
}

/*
    public class ProImpl implements Pro {
        public String className() { return "com.test.Annotation.practice.Animal"; }
        public String methodName() { return "eat"; }
    }
 */
