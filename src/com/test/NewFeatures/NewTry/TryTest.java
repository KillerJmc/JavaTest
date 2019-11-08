package com.test.NewFeatures.NewTry;

public class TryTest {

	public static void main(String[] args) {
		System.out.println("main 代码块中的执行结果为：" + myMethod());
		System.out.println("-----------------------------------------------");
		myMethod2();
		System.out.println("-----------------------------------------------");
		System.out.println("main 代码块中的执行结果为：" + myMethod3());
		System.out.println("-----------------------------------------------");
		myMethod4();
	}

	public static int myMethod() {
		int i = 1;
		try {
			System.out.println("try 代码块被执行！");
			return i;
		} finally {
			++i;
			System.out.println("finally 代码块被执行！");
			System.out.println("finally 代码块中的i = " + i);
		}
		/**
		 * 相当于：
		 try : print("try 代码块被执行！");
			   int num = i;
		 finally : ++i;
				   print ("finally 代码块被执行！");
				   print ("finally 代码块中的i = " + i);
				   return num;
		 */
		
	}
	
	public static void myMethod2() {
		try {
			int i = 1 / 0;
			System.out.println("try 代码块被执行！");
		} catch (Exception e) {
			System.out.println("catch 代码块被执行！");
		} finally {
			System.out.println("finally 代码块被执行！");
		}
	}
	
	public static int myMethod3() {
		try {
			System.out.println("try 代码块被执行！");
			return 1;
		} finally {
			System.out.println("finally 代码块被执行！");
			return 3;
		}
		/**
		 * 相当于：
		 * print("try 代码块被执行！");
		 * int i = 1;
		 * 
		 * print("finally 代码块被执行！");
		 * return 3;
		 *   以下代码不可达：
		 * return i;
		 */
	}
	
	public static void myMethod4() {
		try {
			System.out.println("try 代码块被执行！");
			//直接退出
			System.exit(0);
		} catch (Exception e) {
			System.out.println("catch 代码块被执行！");
		} finally {
			System.out.println("finally 代码块被执行！");
		}
	}
}
