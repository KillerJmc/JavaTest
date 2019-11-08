package com.test.GOF23.StructuralPattern.FlyWeight;

/**
 * 外部状态（UnsharedConcreteFlyWeight）
 * @author Jmc
 */
public class Coordinate {
	private int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
