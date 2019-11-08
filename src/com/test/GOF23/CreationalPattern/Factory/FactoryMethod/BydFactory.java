package com.test.GOF23.CreationalPattern.Factory.FactoryMethod;

public class BydFactory implements CarFactory {

	@Override
	public Car createCar() {
		return new Byd();
	}

}
