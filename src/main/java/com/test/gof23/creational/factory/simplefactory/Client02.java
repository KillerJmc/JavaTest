package com.test.gof23.creational.factory.simplefactory;

/**
 * 简单工厂模式
 * 不完全满足OCP原则
 * 不过一般采用这种方法
 * @author Jmc
 *
 */
public class Client02 {
	public static void main(String[] args) {
		Car c1 = CarFactory.createCar("奥迪");
		Car c2 = CarFactory.createCar("比亚迪");
		c1.run();
		c2.run();
	}
}
