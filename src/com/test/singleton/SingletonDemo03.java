package com.test.singleton;

/**
 * 双重检测锁单例模式(与JVM底层不太配合，不推荐使用)
 * @author Jmc
 *
 */

public class SingletonDemo03 {
	private static SingletonDemo03 instance = null;
	
	public static SingletonDemo03 getInstance() {
		if (instance == null) {
			SingletonDemo03 sc;
			synchronized (SingletonDemo03.class) {
				sc = instance;
				if (sc == null) {
					synchronized (SingletonDemo03.class) {
						if (sc == null) {
							sc = new SingletonDemo03();
						}
					}
					instance = sc;
				}
			}
		}
		return instance;
	}
	
}
