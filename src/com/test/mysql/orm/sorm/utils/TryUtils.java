package com.test.mysql.orm.sorm.utils;

/**
 * Provide the try functions.
 * Copied from com.test.Main.Tools
 * @author Jmc
 */
public class TryUtils {
	/**
	 * a specific Runnable interface
	 */
	@FunctionalInterface
	public interface RunnableThrowsException {
		void run() throws Exception;
	}
	/**
	 * a specific Runnable interface returns variable "T"
	 */
	@FunctionalInterface
	public interface RunnableThrowsAndReturn<T> {
		T run() throws Exception;
	}

	public static void tryThis(RunnableThrowsException r) {
		try {
			r.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static<T> T tryAndReturn(RunnableThrowsAndReturn<T> r) {
		try {
			return r.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
