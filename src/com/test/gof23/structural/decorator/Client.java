/**
 * 装饰者模式
 * 用来增加新功能和改进 / 删除旧功能
 * @author Jmc
 */

package com.test.gof23.structural.decorator;

public class Client {
	public static void main(String[] args) {
		Car car = new Car();
		car.move();
		
		System.out.println("----------增加新的功能：飞行----------");
		FlyCar flyCar = new FlyCar(car);
		flyCar.move();
		
		System.out.println("----------增加新的功能：水里游----------");
		WaterCar waterCar = new WaterCar(car);
		waterCar.move();
		
		System.out.println("---增加以上两个功能：飞行，水里游--------");
		WaterCar waterCar2 = new WaterCar(new FlyCar(car));
		waterCar2.move();
		
		System.out.println("---增加三个功能：飞行，水里游，自动驾驶----------");
		WaterCar waterCar3 = new WaterCar(new FlyCar(new AICar(car)));
		waterCar3.move();
		
		System.out.println("-----------------------");
		car.move();
		System.out.println("------------");
		//把car作为参数，添加autoMove方法
		new AICar(car).move();
		System.out.println("------------");
		//把new AICar(car)作为参数，再添加fly方法
		new FlyCar(new AICar(car)).move();
		System.out.println("------------");
		//把new FlyCar(new AICar(car))作为参数，再添加swim方法
		new WaterCar(new FlyCar(new AICar(car))).move();
		//其间，move方法被重写多次
		//调用的均为新对象的move方法再加上自己想实现的功能
		System.out.println("-----------------------");
	}
}
