package com.test.singleton;

/**
 * DCL(双重检测锁)单例模式
 * @author Jmc
 *
 */

public class SingletonDemo03 {
	/*
		一定要加volatile，
		否则可能因为对象初始化指令重排序导致一个线程在new对象的时候，
		被其他线程直接拿去用，而这个对象还是半初始化的状态（只执行完链接阶段）
	 */
	private static volatile SingletonDemo03 INSTANCE;
	
	public static SingletonDemo03 getInstance() {
		// 业务逻辑省略

		// 锁很细，防止多个线程参与锁竞争，效率高
		if (INSTANCE == null) {
			synchronized (SingletonDemo03.class) {
				/*
					防止两个线程同时突破16行时，
					一个线程进来new对象出去后，
					另一个线程又再次new了一个新对象
				 */
				if (INSTANCE == null) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					INSTANCE = new SingletonDemo03();
				}
			}
		}
		return INSTANCE;
	}
	
}
