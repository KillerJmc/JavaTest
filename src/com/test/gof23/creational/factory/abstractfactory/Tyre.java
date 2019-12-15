package com.test.gof23.creational.factory.abstractfactory;

public interface Tyre {
	//旋转
	void revolve();
}

class LuxuryTyre implements Tyre {
	@Override
	public void revolve() {
		System.out.println("轮胎旋转耐磨！");
	}
}

class LowTyre implements Tyre {
	@Override
	public void revolve() {
		System.out.println("轮胎旋转磨损快！");
	}
}
