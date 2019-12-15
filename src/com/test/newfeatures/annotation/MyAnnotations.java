package com.test.newfeatures.annotation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.*;

@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, MODULE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotations {
	MyAnnotation[] value();
}
