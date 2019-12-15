package com.test.singleton;

/**
 * 测试懒汉式单例模式
 * @author Jmc
 *
 */

public class SingletonDemo02 {
	//延时加载
	private static SingletonDemo02 instance;
	
	private SingletonDemo02() {}
	
	//方法同步，调用效率低!
	public static synchronized SingletonDemo02 getInstance() {
		if (instance == null) {
			instance = new SingletonDemo02();
		}
		return instance;
	}
}





