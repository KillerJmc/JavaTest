package com.test.annotation.knowledge;

import java.lang.annotation.*;

/**
    元注解：用来描述注解的注解
    @Target：描述注解能够作用的位置
    @Retention（保留）：描述注解被保留的阶段
    @Documented：描述注解是否被抽取到api文档中
    @Inherited（遗传）：描述注解是否被子类继承
 */
//可作用于类上，方法上，成员变量上
@Target(value = {ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
//当成被描述的注解，会保留到class字节码文件中，并被JVM读取到
@Retention(RetentionPolicy.RUNTIME)
//将来会被抽取到api文档中
@Documented
//会自动被子类继承
@Inherited
public @interface MyAnno3 {
}
