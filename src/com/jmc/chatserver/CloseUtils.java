package com.jmc.chatserver;

/**
* 功能: 关闭流
*
*/

public class CloseUtils
{
	public static void closeAll(AutoCloseable... items) {
		for (AutoCloseable item : items) {
			try {
				if (item != null) item.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
