package com.test.gof23.structural.facade;

public interface IPhone {
	void getMsg();
}

class Phone implements IPhone {
	@Override
	public void getMsg() {
		System.out.println("收到验证码是xxx。");
		System.out.println("输入验证码点击确定");
	}
}
