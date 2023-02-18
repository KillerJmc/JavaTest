package com.test.apply.aop.cglib.util;

import com.jmc.lang.ref.Pointer;
import com.test.apply.aop.cglib.ArgAnnoAop;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 默认参数AOP增强（只支持成员方法） <br>
 * VM Options: --add-opens java.base/java.lang=ALL-UNNAMED
 * @author Jmc
 */
class DefaultArgAop {
    /**
     * 获取支持默认参数的增强实例
     * @param targetClass 需要支持默认参数的类
     * @param argClassNameToArgValueSupplier 参数类型 -> 从字符串（默认参数注解的值）生成该参数的方法 的map集合
     * @return 支持默认参数的增强实例
     * @param <T> 需要支持默认参数的类的类型
     */
    public static <T> T getInstance(
            Class<T> targetClass,
            Map<String, Function<String, Object>> argClassNameToArgValueSupplier) {

        // 默认参数类型 -> 从字符串（默认参数注解的值）生成该参数的方法 的map集合
        var defaultMap = new HashMap<String, Function<String, Object>>(Map.of(
                Integer.class.getName(), Integer::valueOf,
                Long.class.getName(), Long::valueOf,
                Boolean.class.getName(), Boolean::valueOf,
                String.class.getName(), String::valueOf
        ));

        return ArgAnnoAop.getInstance(targetClass, DefaultArg.class, (paramToAnno, method, args) -> {
            // 参数列表的下标
            var idxPtr = Pointer.of(0);

            paramToAnno.forEach((param, anno) -> {
                var idx = idxPtr.get();

                // 当存在默认参数注解并且传入参数为空的时候才启用默认参数
                if (anno != null && args[idx] == null) {
                    // 参数类型
                    var paramType = param.getType();

                    // 如果默认map能匹配上参数类型，就调用对应的从字符串（默认参数注解的值）生成该参数的方法
                    if (defaultMap.containsKey(paramType.getName())) {
                        args[idx] = defaultMap.get(paramType.getName()).apply(anno.value());
                    } else {
                        // 通过用户定义的map匹配
                        args[idx] = argClassNameToArgValueSupplier.get(paramType.getName()).apply(anno.value());
                    }
                }
                // idx++
                idxPtr.reset(idx + 1);
            });

            // 执行方法
            return method.apply(args);
        });
    }
}
