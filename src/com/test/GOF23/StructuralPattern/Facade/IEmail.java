package com.test.GOF23.StructuralPattern.Facade;

public interface IEmail {
	void sendMsg();
}

class Email implements IEmail {
	@Override
	public void sendMsg() {
		System.out.println("发送注册邮件！");
	}
}

