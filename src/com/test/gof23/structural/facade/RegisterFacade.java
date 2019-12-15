package com.test.gof23.structural.facade;

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
