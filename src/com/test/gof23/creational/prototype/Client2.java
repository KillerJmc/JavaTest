package com.test.gof23.creational.prototype;

import java.sql.Date;

/**
 * 原型模式（深复制）
 * @author Jmc
 *
 */

public class Client2 {
	public static void main(String[] args) throws Exception {
		Date date = new Date(332338838383L);
		
		Sheep2 s1 = new Sheep2("多利妈妈", date);
		System.out.println(s1);
		System.out.println(s1.getName());
		System.out.println(s1.getBirthday());
		
		Sheep2 s2 = (Sheep2) s1.clone();
		//s2的birthday是一个新对象，修改date无影响
		date.setTime(597983299973L);
		System.out.println(s1.getBirthday());
		System.out.println("---------------------------------------------");
		
		s2.setName("多利");
		System.out.println(s2);
		System.out.println(s2.getName());
		System.out.println(s2.getBirthday());
	}
}
