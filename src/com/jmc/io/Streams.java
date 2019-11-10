/**
 * 功能：流处理
 * 时间：
 *		2019.7.16 创建
 *	    2019.8.23 删除readFile方法
 * 
 */

package com.jmc.io;

import java.io.*;
import com.jmc.chatserver.*;

public class Streams {
	public static byte[] read(InputStream in) {
		int i = 0;
		byte[] b = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			while ((i = in.read(b)) != -1) {
				out.write(b,0,i);
			}
		} catch (Exception e) {
			return null;
		} finally {
			CloseUtils.closeAll(in, out);
		}
		
		return out.toByteArray();
	}
}
