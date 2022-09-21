package com.test.gof23.creational.factory.abstractfactory;

public interface CarFactory {
	Engine createEngine();
	Seat createSeat();
	Tyre createTyre();
}
