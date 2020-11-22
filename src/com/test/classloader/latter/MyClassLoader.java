package com.test.classloader.latter;

import com.jmc.io.Files;
import com.test.main.Tools;

import java.io.File;

/**
 * 时间：2020.11.11
 * 作者：Jmc
 * 功能：自定义类加载器
 * 过程：
 *      1. 继承ClassLoader
 *      2. 重写findClass方法，并在其中调用父类的defineClass方法加载类
 *         注：一般不重写loadClass方法，否则会破坏双亲委派机制
 *      3. 在测试方法中调用自定义类加载器的loadClass方法
 *
 */
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String rootPath = Tools.getPath(this);
        File classFile = new File(rootPath.concat(name.replace(".", "/").concat(".class")));

        if (classFile.exists()) {
            byte[] bs = Files.readToBytes(classFile);
            if (bs != null) return super.defineClass(name, bs, 0, bs.length);
        }

        return super.findClass(name);
    }
}
