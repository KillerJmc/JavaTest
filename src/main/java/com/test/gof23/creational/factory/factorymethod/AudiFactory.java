package com.test.gof23.creational.factory.factorymethod;

public class AudiFactory implements CarFactory {

	@Override
	public Car createCar() {
		return new Audi();
	}

}
