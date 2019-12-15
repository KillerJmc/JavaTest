package com.test.gof23.creational.builder;

public class XAirShipDirector implements AirShipDirector {
	private XAirShipBuilder builder;
	
	public XAirShipDirector(XAirShipBuilder builder) {
		this.builder = builder;
	}

	@Override
	public AirShip derectAirShip() {
		Engine e = builder.buildEngine();
		OrbitalModule o = builder.buildOrbitalModule();
		EscapeTower et = builder.buildEscapeTower();
		
		AirShip ship = new AirShip();
		ship.setEngine(e);
		ship.setOrbitalModule(o);
		ship.setEscapeTower(et);
		
		return ship;
	}
}
