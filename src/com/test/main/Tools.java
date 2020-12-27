package com.test.main;

import static com.jmc.lang.extend.Tries.*;

public class Tools {
	public static String getPath(Class<?> c) {
		return "src/" + c.getPackageName().replace(".", "/") + "/";
	}

	public static String getPath(Object o) {
		return "src/" + o.getClass().getPackageName().replace(".", "/") + "/";
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
