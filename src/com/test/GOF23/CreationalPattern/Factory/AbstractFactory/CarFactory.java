package com.test.GOF23.CreationalPattern.Factory.AbstractFactory;

public interface CarFactory {
	Engine createEngine();
	Seat createSeat();
	Tyre createTyre();
}
