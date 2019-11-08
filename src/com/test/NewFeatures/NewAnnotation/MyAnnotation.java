package com.test.NewFeatures.NewAnnotation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.*;

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
