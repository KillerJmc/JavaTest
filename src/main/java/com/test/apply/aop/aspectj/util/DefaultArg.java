package com.test.apply.aop.aspectj.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DefaultArg {
    String value();

    /**
     * 如果是非数字类型，需要传入转换默认参数为指定参数类型的方法 <br>
     * 由于注解不能传入Object，所以只能这么做！
     */
    Class<? extends Function<String, ?>> transferClass() default DefaultTransferClass.class;
}
