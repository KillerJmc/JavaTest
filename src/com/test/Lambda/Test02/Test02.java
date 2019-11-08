package com.test.Lambda.Test02;

import java.util.Comparator;
import java.util.function.Consumer;

public class Test02 {
	public static void main(String[] args) {
		test02();
	}
	
	private static void test01() {
		//jdk 1.7前，必须加final
		//现在编译器帮你加
		int num = 0;
		new Thread(() -> System.out.println("Hello Lambda!" + num)).run();
	}
	
	private static void test02() {
		//只有一个参数括号可以不写
		Consumer<String> con = (x) -> System.out.println(x);
		con.accept("Hello");
	}
	
	private static void test03() {
		Comparator<Integer> com = (x, y) -> {
			System.out.println("函数式接口");
			return Integer.compare(x, y);
		};
	}
	
	private static void test04() {
		//如果只有一条语句，大括号和return都可以省略不写
		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

	}
}
