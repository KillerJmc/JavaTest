package com.test.Annotation.knowledge;

//public interface extends java.lang.annotation.Annotation
public @interface MyAnno {
    int age();
    Person per();
    MyAnno2 anno2();
    String[] strs();
    String name() default "张三";

    //can exist
    /*
    basic data types n();
    String show();
    Person (Enum) per();
    MyAnno2 anno2();
    String[] strs();
     */


    //can't exist
    /*
    void show();
    Worker w();
     */
}
