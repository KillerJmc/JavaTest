package com.test.apply.aop.test;

import com.jmc.lang.ref.Pointer;
import com.test.apply.aop.ParameterAnnoAop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Jmc
 */
public class ParameterAnnoAopTest {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    private @interface DefaultArg {
        String value();
    }

    public static class FileUtils {
        public void what(int a, int b) {
            System.out.printf("what: a = %d, b = %d\n", a, b);
        }

        public void out(
                String s,
                String path,
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
        var fileUtils = createDefaultArgInstance(FileUtils.class, Map.of(
                "java.lang.Boolean", Boolean::valueOf,
                "java.nio.charset.Charset", Charset::forName
        ));

        fileUtils.what(3, 4);
        // 此时会注入默认参数！
        fileUtils.out("Hello", "./a.txt", null, null);
        fileUtils.out("World", "./b.txt", StandardCharsets.UTF_16, null);
        fileUtils.out("!", "./c.txt", null, true);
    }

    @SuppressWarnings("all")
    private static <T> T createDefaultArgInstance(
            Class<T> targetClass,
            Map<String, Function<String, Object>> argClassNameToArgValueSuppliesFromString) {
        return ParameterAnnoAop.getInstance(targetClass, DefaultArg.class, (paramToAnno, method, args) -> {
            var idxPtr = Pointer.of(0);
            paramToAnno.forEach((param, anno) -> {
                var idx = idxPtr.get();

                // 当存在默认参数注解并且传入参数为空的时候才启用默认参数
                if (anno != null && args[idx] == null) {
                    var paramType = param.getType();
                    args[idx] = argClassNameToArgValueSuppliesFromString.get(paramType.getName()).apply(anno.value());
                }
                idxPtr.reset(idx + 1);
            });
            return method.apply(args);
        });
    }
}
