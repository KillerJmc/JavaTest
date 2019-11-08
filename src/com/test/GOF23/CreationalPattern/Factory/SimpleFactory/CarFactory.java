package com.test.GOF23.CreationalPattern.Factory.SimpleFactory;

public class CarFactory {
	public static Car createCar(String type) {
		if ("奥迪".equals(type)) {
			return new Audi();
		} else if ("比亚迪".equals(type)) {
			return new Byd();
		}
		return null;
	}
}
