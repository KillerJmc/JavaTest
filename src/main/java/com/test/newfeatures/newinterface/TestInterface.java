package com.test.newfeatures.newinterface;

public class TestInterface {
	public static void main(String[] args) {
		//类方法优先
		System.out.println(new B().getName());
		//实现多接口相同方法要重写
		System.out.println(new C().getName());
		//接口支持静态方法
		I1.print();
	}
}

interface I1 {
	//java8接口支持默认方法
	default String getName() {
		return "哈哈哈";
	}
	
	//java8接口支持静态方法
	public static void print() {
		System.out.println("woc");
	}
}

class A {
	public String getName() {
		return "嘿嘿嘿";
	}
}

class B extends A implements I1 {}

interface I2 {
	default String getName() {
		return "呵呵呵";
	}
}

class C implements I1, I2 {
	@Override
	public String getName() {
		return I2.super.getName();
	}
}

