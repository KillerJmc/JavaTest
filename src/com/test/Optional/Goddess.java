package com.test.Optional;

public class Goddess {
	private String name;

	public Goddess() {
		super();
	}
	
	public Goddess(String name) {
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
