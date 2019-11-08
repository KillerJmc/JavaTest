package com.test.NewFeatures.NewAnnotation;

import java.lang.reflect.Method;

/**
 * 重复注解与类型注解
 * @author Jmc
 *
 */

public class TestAnnotation {
	@MyAnnotation("Hello")
	@MyAnnotation("World")
	public void show(@MyAnnotation("abc") String str) {
		
	}
	
	public static void test() throws Exception {
		var clazz = TestAnnotation.class;
		Method m1 = clazz.getMethod("show");
		
		MyAnnotation[] ma = m1.getAnnotationsByType(MyAnnotation.class);
		for (MyAnnotation m : ma) {
			System.out.println(m.value());
		}
	}
	
	public static void main(String[] args) throws Exception {
		test();
	}
}
