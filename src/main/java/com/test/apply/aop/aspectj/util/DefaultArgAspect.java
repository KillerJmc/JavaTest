package com.test.apply.aop.aspectj.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 利用AspectJ实现默认参数切面
 * @author Jmc
 */
@Aspect
public class DefaultArgAspect {
    @Around("execution(* *(.., @DefaultArg (*), ..))")
    public Object defaultArg(ProceedingJoinPoint joinPoint) throws Throwable {
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var params = method.getParameters();
        var annotations = method.getParameterAnnotations();
        var args = joinPoint.getArgs();

        // 参数类型 -> 从字符串（默认参数注解的值）生成该参数的方法 的map集合
        var transferMap = new HashMap<String, Function<String, Object>>(Map.of(
                Integer.class.getName(), Integer::valueOf,
                Long.class.getName(), Long::valueOf,
                Boolean.class.getName(), Boolean::valueOf,
                String.class.getName(), String::valueOf
        ));

        for (int i = 0; i < args.length; i++) {
            // 只有存在注解并且参数为null时才填充默认参数
            if (args[i] == null) {
                for (var anno : annotations[i]) {
                    var paramClassName = params[i].getType().getName();
                    var annoClassName = anno.annotationType().getName();

                    if (DefaultArg.class.getName().equals(annoClassName)) {
                        var defaultArgStrValue = ((DefaultArg) anno).value();
                        if (!transferMap.containsKey(paramClassName)) {
                            throw new RuntimeException("Unsupported type: " + paramClassName);
                        }

                        args[i] = transferMap.get(paramClassName).apply(defaultArgStrValue);
                        break;
                    }
                }
            }
        }

        return joinPoint.proceed(args);
    }
}
