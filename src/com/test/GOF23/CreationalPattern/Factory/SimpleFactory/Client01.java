package com.test.GOF23.CreationalPattern.Factory.SimpleFactory;

/**
 * 测试在没有工厂模式的情况下
 * @author Jmc
 *
 */
public class Client01 {
	public static void main(String[] args) {
		Car c1 = new Audi();
		Car c2 = new Byd();
		c1.run();
		c2.run();
	}
}
