package com.test.gof23.creational.factory.simplefactory;

public class CarFactory2 {
	public static Car createAudi() {
		return new Audi();
	}
	
	public static Car createByd() {
		return new Byd();
	}
}
