package com.test.newfeatures.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

//重复注解
@Repeatable(MyAnnotations.class)
//@SuppressWarnings
//忘记怎么写可以去里面复制以下内容
//@Target多了TYPE_PARAMETER
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, MODULE, TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
	String value() default "hh";
}
