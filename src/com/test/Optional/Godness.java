package com.test.Optional;

public class Godness {
	private String name;

	public Godness() {
		super();
	}
	
	public Godness(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "Godness [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
