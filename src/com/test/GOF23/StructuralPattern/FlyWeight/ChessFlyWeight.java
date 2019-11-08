package com.test.GOF23.StructuralPattern.FlyWeight;

/**
 * 享元类（FlyWeight轻量级）
 * @author Jmc
 */
public interface ChessFlyWeight {
	void setColor(String color);
	String getColor();
	void display(Coordinate c);
}

/**
 * 内部状态
 * @author Jmc
 *
 */
class ConcreteChess implements ChessFlyWeight {
	private String color;
	
	public ConcreteChess(String color) {
		this.color = color;
	}

	@Override
	public void setColor(String color) {
		 this.color = color;
	}

	@Override
	public String getColor() {
		return this.color;
	}

	@Override
	public void display(Coordinate c) {
		System.out.println("棋子颜色：" + color);
		System.out.println("棋子位置：" + "(" + c.getX() + ", " + c.getY() + ")");
	}
	
}
