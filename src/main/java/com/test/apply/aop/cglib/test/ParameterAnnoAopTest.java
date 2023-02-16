package com.test.apply.aop.cglib.test;

import com.test.apply.aop.cglib.util.DefaultArg;
import com.test.apply.aop.cglib.util.DefaultArgAop;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Jmc
 */
public class ParameterAnnoAopTest {
    public static class FileUtils {
        public void what(int a, int b) {
            System.out.printf("what: a = %d, b = %d\n", a, b);
        }

        public void out(
                String s,
                @DefaultArg("./out.txt") String path,
                @DefaultArg("UTF-8") Charset charset,
                @DefaultArg("false") Boolean appendMode) {
            System.out.printf("正在输出字符串 '%s' 到文件 '%s'，文件编码是：'%s'，是否为追加模式：'%s'\n",
                s,
                path,
                charset,
                appendMode
            );
        }
    }

    public static void main(String[] a) {
        // 获取实例，配置参数类型和从字符串生成该参数的方法
        var fileUtils = DefaultArgAop.getInstance(FileUtils.class, Map.of(
                Charset.class.getName(), Charset::forName
        ));

        fileUtils.what(3, 4);
        // 此时会注入默认参数！
        fileUtils.out("Hello", "./a.txt", null, null);
        fileUtils.out("World", null, StandardCharsets.UTF_16, null);
        fileUtils.out("!", null, null, true);
    }
}
