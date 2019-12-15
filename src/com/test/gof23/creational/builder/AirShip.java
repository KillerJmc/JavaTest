package com.test.gof23.creational.builder;

public class AirShip {
	private OrbitalModule orbitalModule; //轨道舱
	private Engine engine; //引擎
	private EscapeTower escapeTower; //逃逸塔
	
	public void launch() {
		System.out.println("发射！");
	}
	
	@Override
	public String toString() {
		return "AirShip [orbitalModule=" + orbitalModule + ", \nengine=" + engine + ", \nescapeTower=" + escapeTower + "]";
	}

	public OrbitalModule getOrbitalModule() {
		return orbitalModule;
	}
	
	public void setOrbitalModule(OrbitalModule orbitalModule) {
		this.orbitalModule = orbitalModule;
	}
	
	public Engine getEngine() {
		return engine;
	}
	
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	
	public EscapeTower getEscapeTower() {
		return escapeTower;
	}
	
	public void setEscapeTower(EscapeTower escapeTower) {
		this.escapeTower = escapeTower;
	}
}

class OrbitalModule {
	@Override
	public String toString() {
		return "OrbitalModule [name=" + name + "]";
	}

	private String name;

	public OrbitalModule(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class Engine {
	@Override
	public String toString() {
		return "Engine [name=" + name + "]";
	}

	private String name;
	
	public Engine(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

class EscapeTower {
	@Override
	public String toString() {
		return "EscapeTower [name=" + name + "]";
	}

	private String name;
	
	public EscapeTower(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}