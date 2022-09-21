package com.test.gof23.structural.facade;

public interface IEmail {
	void sendMsg();
}

class Email implements IEmail {
	@Override
	public void sendMsg() {
		System.out.println("发送注册邮件！");
	}
}

