package com.test.apply.aop;

import com.jmc.lang.Tries;

import java.lang.annotation.Annotation;
import java.util.function.Function;

/**
 * @author Jmc
 */
public class ClassAnnoAop {
    /**
     *
     * @param <Anno>
     */
    public interface CallBack<Anno extends Annotation> {
        Object invoke(Anno classAnno, Function<Object[], Object> method, Object[] args);
    }

    public static <T, Anno extends Annotation> T getInstance(
            Class<T> targetClass,
            Class<Anno> annotationClass,
            CallBack<Anno> callback) {
        return DynamicProxy.getInstance(targetClass, (obj, method, args0, proxy) -> {
            // 获取类注解
            var anno = targetClass.getDeclaredAnnotation(annotationClass);

            return callback.invoke(
                    anno,
                    args -> Tries.tryReturnsT(() -> proxy.invokeSuper(obj, args)),
                    args0
            );
        });
    }
}
