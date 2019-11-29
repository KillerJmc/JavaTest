package com.test.ORM.SORM.Utils;

/**
 * Copied from com.test.Main.Tools
 * @author Jmc
 */
public class TryUtils {
	public interface RunnableThrowsException {
		void run() throws Exception;
	}
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
