package com.test.ClassLoader;

public class Main {
	public static void main(String[] args) throws Exception {
		test01();
	}
	
	public static void test01() throws Exception {
		FileSystemClassLoader loader = 
			new FileSystemClassLoader("C:/Jmc/Temp/ClassLoaderTest",false);
		Class<?> c = loader.loadClass("com.a.c.HellWorld");
		Class<?> c1 = loader.loadClass("com.test.ClassLoader.Main");
		Class<?> c2 = loader.loadClass("java.lang.String");
		System.out.println(c);
		System.out.println(c1);
		System.out.println(c2);
	}
	
	public static void test02() throws Exception {
		Thread.currentThread().setContextClassLoader(new FileSystemClassLoader("C:/Jmc/Temp/ClassLoaderTest", false));
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class<?> c = loader.loadClass("com.a.c.HellWorld");
		System.out.println(c);
	}
	
	private static void test03() {
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		ClassLoader one = loader.getParent();
		System.out.println(loader);
		System.out.println(one);
		System.out.println(one.getParent());
	}
	
}
