package com.test.gof23.structural.decorator;

/**
 * 抽象组件
 */
public interface ICar {
	void move();
}

/**
 * 具体构建对象（真实对象）
 */
class Car implements ICar {
	@Override
	public void move() {
		System.out.println("陆地上跑！");
	}
}

/**
 * 装饰者（Decorator）
 */
class SuperCar implements ICar {
	protected ICar car;
	
	public SuperCar(ICar car) {
		this.car = car;
	}

	@Override
	public void move() {
		car.move();
	}
}

/**
 * 具体装饰者（ConcreteDecorator）
 * 注意：它调的不是陆地车的move方法
 * 	      而是调传进来的新车的move方法
 */
class FlyCar extends SuperCar {
	public FlyCar(ICar car) {
		super(car);
	}
	
	public void fly() {
		System.out.println("天上飞！");
	}
	
	@Override
	public void move() {
		super.move();
		fly();
	}
}

class WaterCar extends SuperCar {
	public WaterCar(ICar car) {
		super(car);
	}
	
	public void swim() {
		System.out.println("水上游！");
	}
	
	@Override
	public void move() {
		super.move();
		swim();
	}
}

class AICar extends SuperCar {
	public AICar(ICar car) {
		super(car);
	}
	
	public void autoMove() {
		System.out.println("自动驾驶！");
	}
	
	@Override
	public void move() {
		super.move();
		autoMove();
	}
}





