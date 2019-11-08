package com.test.Annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Main {
	
	@SuppressWarnings("all")
	public static void main(String[] args) throws Exception {
		
		//获得类所有有效注解
		Class c = Class.forName("com.test.Annotation.Student");
		Annotation[] as = c.getAnnotations();
		for(Annotation a : as) {
			System.out.println(a);
		}
		
		//获得类指定的注解
		XTable table = (XTable) c.getAnnotation(XTable.class);
		System.out.println(table.value());
		
		//获得类属性的注解
		Field nameF = c.getDeclaredField("name");
		XField name = nameF.getAnnotation(XField.class);
		System.out.println(name.columnName()
				+ "--" + name.type() + "--" + name.length());
	}
}