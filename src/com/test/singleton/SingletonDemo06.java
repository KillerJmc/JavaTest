package com.test.singleton;

import java.io.Serializable;

/**
 * 测试懒汉式单例模式的破解
 * @author Jmc
 *
 */

//反序列化必须实现Serializable
public class SingletonDemo06 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//延时加载
	private static SingletonDemo06 instance;
	
	//防止反射
	private SingletonDemo06() {
		if (instance != null) {
			throw new RuntimeException();
		}
	}
	
	//方法同步，调用效率低!
	public static synchronized SingletonDemo06 getInstance() {
		if (instance == null) {
			instance = new SingletonDemo06();
		}
		return instance;
	}
	
	//反序列化定义此方法可以直接返回制定的对象
	private Object readResolve() {
		return instance;
	}
}





