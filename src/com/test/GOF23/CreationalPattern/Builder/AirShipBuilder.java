package com.test.GOF23.CreationalPattern.Builder;

public interface AirShipBuilder {
	Engine buildEngine();
	OrbitalModule buildOrbitalModule();
	EscapeTower buildEscapeTower();
}
