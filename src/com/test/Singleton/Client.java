package com.test.Singleton;

import java.lang.reflect.Method;

/**
 * 占用资源少，不需要延时加载：枚举式好于饿汉式
 * 占用资源多，需要延时加载：静态内部类式好于懒汉式
 * @author Jmc
 *
 */

public class Client {
	public static void main(String[] args) throws Exception {
		System.out.println(compare(01));
		System.out.println(compare(02));
		System.out.println(compare(03));
		System.out.println(compare(04));
		System.out.println(SingletonDemo05.INSTANCE == SingletonDemo05.INSTANCE);
	}
	
	@SuppressWarnings("all")
	private static boolean compare(int num) throws Exception {
		//Class获取多次，都只指向同一个对象
		Class<?> c = Class.forName("com.test.Singleton.SingletonDemo0" + num);
		Method m = c.getMethod("getInstance", null);
		
		return m.invoke(null, null).equals(m.invoke(null, null));
	}
}
