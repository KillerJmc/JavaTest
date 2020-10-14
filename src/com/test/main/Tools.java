package com.test.main;

public class Tools {
	public static String getPath(Class<?> c) {
		return "src/" + c.getPackageName().replace(".", "/") + "/";
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
