package com.test.GOF23.CreationalPattern.Builder;

public class XAirShipBuilder implements AirShipBuilder {
	@Override
	public Engine buildEngine() {
		System.out.println("构建X发动机");
		return new Engine("X发动机");
	}

	@Override
	public OrbitalModule buildOrbitalModule() {
		System.out.println("构建X轨道舱");
		return new OrbitalModule("X轨道舱");
	}

	@Override
	public EscapeTower buildEscapeTower() {
		System.out.println("构建X逃逸塔");
		return new EscapeTower("X逃逸塔");
	}
}
