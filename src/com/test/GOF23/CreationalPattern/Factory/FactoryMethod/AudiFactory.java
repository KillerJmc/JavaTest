package com.test.GOF23.CreationalPattern.Factory.FactoryMethod;

public class AudiFactory implements CarFactory {

	@Override
	public Car createCar() {
		return new Audi();
	}

}
