package com.test.gof23.creational.prototype;

import java.sql.Date;

/**
 * 测试原型模式（浅克隆），克隆的只是引用地址
 * @author Jmc
 *
 */

public class Client {
	public static void main(String[] args) throws Exception {
		Date date = new Date(332338838383L);
		
		Sheep s1 = new Sheep("多利妈妈", date);
		System.out.println(s1);
		System.out.println(s1.getName());
		System.out.println(s1.getBirthday());
		
		Sheep s2 = (Sheep) s1.clone();
		//浅复制，date对s2依然有影响
		date.setTime(597983299973L);
		System.out.println(s1.getBirthday());
		System.out.println("---------------------------------------------");
		
		s2.setName("多利");
		System.out.println(s2);
		System.out.println(s2.getName());
		System.out.println(s2.getBirthday());
	}
}
