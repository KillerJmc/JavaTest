/*
 * 作者: Jmc
 * 时间: 2019.1.26
 * 功能: 类的反编译
 */
 
package com.jmc.decompile;
 
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class ClassDecompile
{
    //配置文件路径
    private static String path = "/sdcard/class.properties";
    
    //构造方法
    public ClassDecompile() {}
    public ClassDecompile(String path) {
        this.path = path;
    }
    
    //反编译
    public static String decompile(String... className) throws Exception {
        
        //类名
        String str;     
        //类名参数为空时通过properties文件获取
        if (className.length == 0) {
            Properties ps = new Properties();           
            FileInputStream fis = new FileInputStream(path);
            ps.load(fis);
            fis.close();
            str = ps.getProperty("className");
        } else {
            str = className[0];
        }

        //实现类
        Class c = Class.forName(str);

        //实现接口数组,参数数组,构造方法数组和方法数组
        Class[] ins = c.getInterfaces();
        Field[] fs = c.getDeclaredFields();
        Constructor[] cts = c.getConstructors();
        Method[] md = c.getDeclaredMethods();

        //用StringBuffer储存结果
        StringBuffer sb = new StringBuffer();

        //如果类修饰符不存在就不加空格
        String classModifier = "";
        if (c.getModifiers() == 0) {
            sb.append("class " + c.getSimpleName());
        } else {
            classModifier = Modifier.toString(c.getModifiers());
            if (classModifier.contains("interface")) {
                sb.append(classModifier.replace("abstract ", "") + " " + c.getSimpleName());
            } else {
                sb.append(classModifier + " class " + c.getSimpleName());
            }
        }
        //若不止继承Object则添加父类
        Class superClass = c.getSuperclass();
        if (superClass != null && !"java.lang.Object".equals(superClass.getName())) {
            sb.append(" extends " + c.getSuperclass().getSimpleName());
        }
        //添加实现的接口
        if (ins.length != 0) {
            sb.append(" implements ");
            int count = 0;
            for (Class in: ins) {
                //若为最后一个就不加逗号
                if (count == ins.length - 1) {
                    sb.append(in.getSimpleName());
                } else {
                    sb.append(in.getSimpleName() + ", ");
                }
                count++;
            }
        }
        //加左大括号并换行
        sb.append(" {\n");

        //添加类中参数
        for (Field field : fs) {
            //每行添加tab
            sb.append("\t");
            //如果参数存在修饰符就添加
            if (field.getModifiers() != 0) {
                sb.append(Modifier.toString(field.getModifiers()) + " ");
            }
            //添加参数属性(int, String等)
            sb.append(field.getType().getSimpleName() + " ");
            //添加参数名并分号换行结束
            sb.append(field.getName() + ";\n");
        }
        //换行
        sb.append("\n");

        //添加构造方法
        for (Constructor ct : cts) {
            //每行添加tab
            sb.append("\t");
            //如果存在修饰符就添加
            if (ct.getModifiers() != 0){
                sb.append(Modifier.toString(ct.getModifiers()) + " ");
            }
            //添加构造方法名
            sb.append(c.getSimpleName() + "(");
            //传入方法参数数组
            Class[] cs = ct.getParameterTypes();
            //如果没有参数就结束
            if (cs.length == 0) {
                sb.append(")" + " {}\n\n");
            } else {
                //计数的int
                int count = 0;
                //遍历方法参数类型数组
                for (Class mdParaType : cs) {
                    //添加参数类型
                    sb.append(mdParaType.getSimpleName() + " ");

                    //添加参数名称
                    sb.append("p" + (count + 1));

                    //如果是最后一个参数
                    if (count == cs.length - 1) {
                        //不加逗号换行结束
                        sb.append(")" + " {}\n\n");
                    } else {
                        //加逗号
                        sb.append(", ");
                    }
                    //计数器+1
                    count++;
                }
            }
        }

        //添加方法
        for (Method method : md) {
            //每行添加tab
            sb.append("\t");
            //如果方法存在修饰符就添加
            if (method.getModifiers() != 0){
                String methodModifier = Modifier.toString(method.getModifiers());
                if (classModifier.contains("interface")) {
                    methodModifier = methodModifier.replace(" abstract", "");
                }
                sb.append(methodModifier + " ");
            }           
            //添加返回值类型
            sb.append(method.getReturnType().getSimpleName() + " ");
            //添加方法名
            sb.append(method.getName() + "(");
            //传入方法参数数组
            Class[] cs = method.getParameterTypes();  
            //如果没有参数就结束
            if (cs.length == 0) {
                sb.append(")" + " {}\n\n");
            } else {
                //计数的int
                int count = 0;
                //遍历方法参数类型数组
                for (Class mdParaType : cs) {     
                    //添加参数类型
                    sb.append(mdParaType.getSimpleName() + " "); 

                    //添加参数名称
                    sb.append("p" + (count + 1));

                    //如果是最后一个参数
                    if (count == cs.length - 1) {       
                        //不加逗号换行结束
                        sb.append(")" + " {}\n\n");
                    } else {
                        //加逗号
                        sb.append(", ");
                    }
                    //计数器+1
                    count++;
                }
            }            
        }

        //删除最后一个换行符
        sb.delete(sb.length() - 1, sb.length());
        //添加右大括号
        sb.append("}");
        //输出StringBuffer
        return sb.toString();
    }

    public static String decompile(Class c) throws Exception {
        return decompile(c.getName());
    }
}
