package com.test.javassist;

import com.jmc.lang.reflect.Reflects;
import javassist.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

/**
 * @author Jmc
 */
public class Main {
    @Test
    public void createNew() throws Exception {
        // 获取class池（用于获取javassist专用的CtClass
        var pool = ClassPool.getDefault();
        // 创建一个class，并指定类名
        var ctClass = pool.makeClass("com.jmc.Customer");

        // 添加Integer id属性
        var idField = new CtField(pool.get("java.lang.Integer"), "id", ctClass);
        idField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(idField, "Integer.valueOf(1)");

        // 添加id的setter和getter
        ctClass.addMethod(CtNewMethod.getter("getId", idField));
        ctClass.addMethod(CtNewMethod.setter("setId", idField));

        // 添加无参构造方法
        var emptyCtor = new CtConstructor(new CtClass[0], ctClass);
        emptyCtor.setBody("{}");
        ctClass.addConstructor(emptyCtor);

        // 添加指定id的有参构造（$0 = this, $1, $2... = 参数）
        var idCtor = new CtConstructor(new CtClass[]{ pool.get("java.lang.Integer") }, ctClass);
        idCtor.setBody("{ $0.id = $1; }");
        ctClass.addConstructor(idCtor);

        // 将class文件保存到temp文件夹
        ctClass.writeFile("temp");

        // 创建class并加载进JVM
        var c = ctClass.toClass();

        var instance = Reflects.newInstance(c);
        var id = Reflects.getFieldValue(instance, "id");
        System.out.println(id);
    }

    @Data
    @AllArgsConstructor
    public static class Student {
        private Integer id;
        private String name;
    }

    @Test
    public void createFromCurrent() throws Exception {
        var pool = ClassPool.getDefault();
        // 获取将要修改的类，转换为CtClass对象
        var ctClass = pool.get(Student.class.getName());
        // 重命名，避免重复加载报错
        ctClass.setName("com.jmc.Student");

        // 获取getId方法
        var getIdMethod = ctClass.getDeclaredMethod("getId");
        // 在方法前插入代码（将id属性hack成666）
        getIdMethod.insertBefore("""
            System.out.println("Javassist pre insert -> hack id to 666");
            $0.id = Integer.valueOf(666);
        """);
        // 在方法后插入代码
        getIdMethod.insertAfter("""
            System.out.println("Javassist post insert!");
        """);

        // 定义一个返回值为void，名为wow的方法
        var wowMethod = CtNewMethod.make("""
            public void wow() {
                System.out.println("Wow! You have been hacked by Javassist!");
            }
        """, ctClass);
        ctClass.addMethod(wowMethod);

        var c = ctClass.toClass();

        var stu = Reflects.newInstance(c, 1, "Jmc");
        Integer id = Reflects.invokeMethod(stu, "getId");
        // 查看hack效果
        System.out.println("getId -> " + id);

        // 查看方法插入效果
        Reflects.invokeMethod(stu, "wow");
    }
}

