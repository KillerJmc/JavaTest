package com.test.apply.proxy;

import com.jmc.lang.Tries;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;

/**
 * @author Jmc
 */
public class ParameterAnnoAop {
    public interface CallBack<Anno extends Annotation> {
        Object invoke(Map<Parameter, Anno> paramToAnno, Function<Object[], Object> method, Object[] args);
    }

    @SuppressWarnings("unchecked")
    public static <T, Anno extends Annotation> T getInstance(
            Class<T> targetClass,
            Class<Anno> annotationClass,
            CallBack<Anno> callback) {
        return DynamicProxy.getInstance(targetClass, (obj, method, args, proxy) -> {
            // 获取方法参数的注解
            var annotations = method.getParameterAnnotations();

            var params = method.getParameters();
            var paramToAnno = new LinkedHashMap<Parameter, Anno>();

            // 提前放入所有参数
            for (var param : params) {
                paramToAnno.put(param, null);
            }

            // 是否存在自定义的注解
            var containsTargetAnno = false;

            // 如果存在注解就放入
            for (int i = 0; i < annotations.length; i++) {
                for (var anno : annotations[i]) {
                    if (anno.annotationType().getName().equals(annotationClass.getName())) {
                        containsTargetAnno = true;
                        paramToAnno.put(params[i], (Anno) anno);
                        break;
                    }
                }
            }

            // 如果存在自定义的注解就执行回调方法
            if (containsTargetAnno) {
                return callback.invoke(
                        paramToAnno,
                        args0 -> Tries.tryReturnsT(() -> proxy.invokeSuper(obj, args0)),
                        args
                );
            }

            // 否则直接返回
            return proxy.invokeSuper(obj, args);
        });
    }
}
