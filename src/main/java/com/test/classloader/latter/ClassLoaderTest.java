package com.test.classloader.latter;

import org.junit.Test;

import java.lang.reflect.Method;

public class ClassLoaderTest {
    @Test
    public void test1() throws Exception {
        ClassLoader loader = new MyClassLoader();
        Class<?> c = loader.loadClass("jmc.ok.MyNBClazz");
        Object myNBClazz = c.getConstructor().newInstance();

        Method memberGetOwner = c.getMethod("getOwner");
        Method staticNB = c.getMethod("staticNB", String.class);
        Method memberNB = c.getMethod("memberNB", String.class);

        String owner = (String) memberGetOwner.invoke(myNBClazz);
        System.out.println("owner = " + owner);

        staticNB.invoke(null, "LBW");
        memberNB.invoke(myNBClazz, "OKSir");
    }

    @Test
    public void test2() throws Exception {
        ClassLoader loader = new MyClassLoader();
        ClassLoader loader2 = new MyClassLoader();
        Class<?> c = loader.loadClass("jmc.ok.MyNBClazz");
        Class<?> c2 = loader.loadClass("jmc.ok.MyNBClazz");
        Class<?> c3 = loader2.loadClass("jmc.ok.MyNBClazz");

        System.out.println(c == c2);
        // 确定类是否相同要包名类名相同，类加载器也要相同
        System.out.println(c == c3);
    }
}
