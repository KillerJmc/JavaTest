package com.test.keytest;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String correctAnswer = "abcdefghijklmnopqrstuvwxyz";
		System.out.println("\n按回车键以开始。");
		in.next();
		
		while (true) {
			long startTime = System.currentTimeMillis();
			boolean flag = in.next().equals(correctAnswer); 
			long endTime = System.currentTimeMillis();
			int time = (int) ((endTime - startTime) / 1000);
			System.out.println("\n回答"
				+ (flag ? "正确，耗时" + time + "秒" : "错误!"));
			System.out.println("\n按回车键以继续...");
			in.next();
		}
	}

}
