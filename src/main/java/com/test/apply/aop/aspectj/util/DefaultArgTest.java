package com.test.apply.aop.aspectj.util;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * VM Options: -Djdk.attach.allowAttachSelf=true --add-opens java.base/java.lang=ALL-UNNAMED
 * @author Jmc
 */
class DefaultArgTest {
    private static class FileUtils {
        private static class StringToCharset implements Function<String, Charset> {
            @Override
            public Charset apply(String s) {
                return Charset.forName(s);
            }
        }

        public static void out(
                String s,
                @DefaultArg("./out.txt") String path,
                // 非数字类型需要提供转换方法！
                @DefaultArg(value = "UTF-8", transferClass = StringToCharset.class) Charset charset,
                @DefaultArg("false") Boolean appendMode) {
            System.out.printf("正在输出字符串 '%s' 到文件 '%s'，文件编码是：'%s'，是否为追加模式：'%s'\n",
                    s,
                    path,
                    charset,
                    appendMode
            );
        }

        public FileUtils(@DefaultArg("Hello") String s) {
            System.out.println("正在创建实例，s = " + s);
        }
    }

    public static void attachAspectjAgent() {
        var pathToAgentJar = new File("temp/aspectjweaver-1.9.19.jar").getAbsolutePath();
        try {
            var pid = ProcessHandle.current().pid();
            VirtualMachine vm = VirtualMachine.attach(String.valueOf(pid));
            vm.loadAgent(pathToAgentJar);
            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {
        attachAspectjAgent();

        // 此时会注入默认参数！
        FileUtils.out("Hello", "./a.txt", null, null);
        FileUtils.out("World", null, StandardCharsets.UTF_16, null);
        FileUtils.out("!", null, null, true);

        // 构造方法也支持哦！
        new FileUtils(null);
    }
}
