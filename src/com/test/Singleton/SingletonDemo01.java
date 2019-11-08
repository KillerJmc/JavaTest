package com.test.Singleton;

/**
 * 测试饿汉式单例模式
 * @author Jmc
 *
 */

public class SingletonDemo01 {
	//类加载时，线程安全(没有延迟加载的优势)
	private static SingletonDemo01 instance = new SingletonDemo01();
	
	private SingletonDemo01() {}
	
	//方法没有同步，调用效率高
	public static SingletonDemo01 getInstance() {
		return instance;
	}
}
