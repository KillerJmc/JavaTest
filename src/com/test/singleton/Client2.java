/**
 * 测试反射和反序列化破解
 */

package com.test.singleton;

import java.io.*;

public class Client2 {
	public static void main(String[] args) throws Exception {
//		test01();
		test02();
	}

	/**
	 * 反射方法直接调用私有构造器
	 */
	@SuppressWarnings("all")
	private static void test01() throws Exception {
		//懒汉式单例模式
		var s1 = SingletonDemo06.getInstance();
		var s2 = SingletonDemo06.getInstance();
		System.out.println(s1);
		System.out.println(s2);
		
		var clazz = Class.forName("com.test.singleton.SingletonDemo06");
		var cons = clazz.getDeclaredConstructor();
		
		cons.setAccessible(true);
		
		//反射破解被防止
		var s3 = cons.newInstance();
		var s4 = cons.newInstance();
		System.out.println(s3);
		System.out.println(s4);
	}
	
	/**
	 * 反序列化方法构建多个对象
	 * @throws Exception 
	 */
	private static void test02() throws Exception {
		var s1 = SingletonDemo06.getInstance();
		System.out.println(s1);
		var path = "temp/a.txt";
		
		try (var out = new FileOutputStream(path, false);
			 var oos = new ObjectOutputStream(out)){
			oos.writeObject(s1);
		}

		//被readResolve反击了
		try (var in = new FileInputStream(path);
			 var ois = new ObjectInputStream(in)) {
			SingletonDemo06 s2 = (SingletonDemo06) ois.readObject();
			System.out.println(s2);
		}
	}
}
