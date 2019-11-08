package com.test.Singleton;

/**
 * 测试静态内部类单例模式
 * 优点：线程安全，调用效率高，延时加载
 * @author Jmc
 *
 */

public class SingletonDemo04 {
	//静态内部类在自身被调用时才加载(主类加载时也不加载)
	private static class SingletonClasssInstance {
		private final static SingletonDemo04 instance = new SingletonDemo04();
	}
	
	//方法没有同步，调用效率高
	public static SingletonDemo04 getInstance() {
		return SingletonClasssInstance.instance;
	}
}
