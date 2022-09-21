package com.test.gof23.creational.factory.abstractfactory;

/**
 * 抽象工厂模式（类似工厂方法模式）
 * 但只能添加产品族，不能添加产品
 * @author Jmc
 *
 */

public class Client {
	public static void main(String[] args) {
		CarFactory factory = new LuxuryCarFactory();
		Engine e = factory.createEngine();
		e.run();
		e.start();
		factory.createSeat().feeling();
	}
}
