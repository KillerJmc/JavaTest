package com.test.GOF23.CreationalPattern.Factory.FactoryMethod;

/**
 * 工厂方法模式
 * 满足OCP原则，不修改已有工厂类
 * 不过一般不采用这种方法，关系太复杂
 * @author Jmc
 *
 */

public class Client {
	public static void main(String[] args) {
		Car c1 = new AudiFactory().createCar();
		Car c2 = new BydFactory().createCar();
		c1.run();
		c2.run();
	}
}
