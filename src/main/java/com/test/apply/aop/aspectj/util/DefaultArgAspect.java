package com.test.apply.aop.aspectj.util;

import com.jmc.lang.reflect.Reflects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.math.BigDecimal;
import java.math.BigInteger;
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
                Byte.class.getName(), Byte::valueOf,
                Short.class.getName(), Short::valueOf,
                Integer.class.getName(), Integer::valueOf,
                Long.class.getName(), Long::valueOf,
                Float.class.getName(), Float::valueOf,
                Double.class.getName(), Double::valueOf,
                Character.class.getName(), s -> s.charAt(0),
                Boolean.class.getName(), Boolean::valueOf,
                BigInteger.class.getName(), BigInteger::new,
                BigDecimal.class.getName(), BigDecimal::new
        ));
        transferMap.put(String.class.getName(), s -> s);

        for (int i = 0; i < args.length; i++) {
            // 只有存在注解并且参数为null时才填充默认参数
            if (args[i] == null) {
                for (var anno : annotations[i]) {
                    var paramClassName = params[i].getType().getName();
                    var annoClassName = anno.annotationType().getName();

                    if (DefaultArg.class.getName().equals(annoClassName)) {
                        var defaultArgStrValue = ((DefaultArg) anno).value();
                        if (transferMap.containsKey(paramClassName)) {
                            args[i] = transferMap.get(paramClassName).apply(defaultArgStrValue);
                            break;
                        }

                        var transferClass = ((DefaultArg) anno).transferClass();
                        var transferInstance = Reflects.newInstance(transferClass);
                        args[i] = transferInstance.apply(defaultArgStrValue);
                        break;
                    }
                }
            }
        }

        return joinPoint.proceed(args);
    }
}
