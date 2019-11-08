package com.test.GOF23.BehaviorPattern.Strategy;

/**
 * 负责和具体的策略类交互
 * 具体的算法和直接的客户端调用分离，使得算法独立
 * 
 * 如果使用spring依赖注入功能，
 * 还可以通过配置文件，动态注入不同的策略对象，动态地切换不同算法
 * @author Jmc
 *
 */
public class Context {
	/**
	 * 当前采用的算法对象
	 */
	private Strategy strategy;

	//方法一
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	//方法二
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public void printPrice(double originalPrice) {
		System.out.println("您该报价：" + strategy.getPrice(originalPrice));
	}
}
