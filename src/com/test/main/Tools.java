package com.test.main;

import static com.jmc.lang.Tries.*;

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

	public static void newLine() {
		System.out.println("-".repeat(60));
	}

	public static void newLine(RunnableThrowsException r) {
		newLine();
		try { r.run(); } catch (Exception e) { e.printStackTrace(); }
		newLine();
	}
}
