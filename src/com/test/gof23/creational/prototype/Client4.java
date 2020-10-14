/**
 * 测试克隆效率
 * new时候越耗时，差异越大
 */

package com.test.gof23.creational.prototype;

import java.sql.Date;

import com.test.main.Tools;

import static com.jmc.lang.Timers.milliTimer;

public class Client4 {
	public static void main(String[] args) {
		//执行一亿次创建
		testNormal(100000000);
		System.out.println("---------------------------------------------");
		testClone(100000000);
	}
	
	private static void testClone(int times) {
		Sheep2 s = new Sheep2("多利", new Date(23908442098428L));
		milliTimer(() -> {
			for (int i = 0; i < times; i++) {
				try {
					Sheep2 t = (Sheep2) s.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("克隆创建");
		});
	}
	
	private static void testNormal(int times) {
		milliTimer(() -> {
			for (int i = 0; i < times; i++) {
				Sheep2 t = new Sheep2("多利", new Date(23908442098428L));
			}
			System.out.println("new创建");
		});
	}
}












