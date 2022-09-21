package com.test.gof23.creational.factory.abstractfactory;

public interface Seat {
	void feeling();
}

class LuxurySeat implements Seat {
	@Override
	public void feeling() {
		System.out.println("坐得舒服！");
	}
}

class LowSeat implements Seat {
	@Override
	public void feeling() {
		System.out.println("坐得感觉很差！");
	}
}
