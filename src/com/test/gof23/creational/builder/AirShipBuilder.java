package com.test.gof23.creational.builder;

public interface AirShipBuilder {
	Engine buildEngine();
	OrbitalModule buildOrbitalModule();
	EscapeTower buildEscapeTower();
}
