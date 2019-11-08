package com.test.GOF23.StructuralPattern.Facade;

public class RegisterFacade {
	/**
	 * 登记，注册
	 */
	public static void register() {
		new Email().sendMsg();
		new Phone().getMsg();
		System.out.println("注册成功！");
	}
}
