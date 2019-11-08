package com.test.Singleton;

/**
 * 测试枚举式单例模式(没有延时加载)
 * 可以防止反射和反序列化
 * @author Jmc
 *
 */

public enum SingletonDemo05 {
	//这个枚举元素，本身就是单例对象！
	INSTANCE;
	
	//添加自己需要的操作!
	@SuppressWarnings("all")
	private void singletonOperation() {
		
	}
}
