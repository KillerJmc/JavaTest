package com.test.Annotation.practice2;

import com.jmc.io.Files;
import com.test.Main.Tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 当主方法执行后，会自动检测加了@Check注解的所有方法，判断方法是否有异常，并记录到文件中
 */
@SuppressWarnings("all")
public class TestCheck {
    public static void main(String[] args) {
        var cal = new Calculator();
        var c = cal.getClass();
        int n = 0;
        var sb = new StringBuilder();

        for (var m : c.getMethods()) {
            if (m.isAnnotationPresent(Check.class)) {
                try {
                    m.invoke(cal);
                } catch (Exception e) {
                    n++;
                    sb.append(m.getName() + "方法出异常了\n");
                    sb.append("异常名称：" + e.getCause().getClass().getSimpleName() + "\n" );
                    var cause = e.getCause().toString();
                    //e.getMessage is null, so use this fn.
                    sb.append("异常原因：" + cause.substring(cause.indexOf(":") + 1).trim() + "\n");
                    sb.append("---------------------------------------------------------\n");
                }
            }
        }
        sb.append("本次测试一共出现" + n + "次异常");

        Files.out(sb.toString(), Tools.getFilePath(TestCheck.class, "log.txt"), false);
    }
}
