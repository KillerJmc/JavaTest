package com.test.GOF23.CreationalPattern.Factory.SimpleFactory;

public class CarFactory2 {
	public static Car createAudi() {
		return new Audi();
	}
	
	public static Car createByd() {
		return new Byd();
	}
}
