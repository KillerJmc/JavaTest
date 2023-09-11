package com.test.main;

public class Tools {
	public static String getCwd(Class<?> c) {
		return "src/" + c.getPackageName().replace(".", "/") + "/";
	}

	public static String getCwd(Object o) {
		return "src/" + o.getClass().getPackageName().replace(".", "/") + "/";
	}
}
