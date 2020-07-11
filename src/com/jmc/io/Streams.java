/**
 * 功能：流处理
 * 时间：
 *		2019.7.16   创建
 *	    2019.8.23   删除readFile方法
 *		2020.4.7    修改现有方法，添加一系列序列化方法
 *		2020.4.9    关于文件与流的out方法移动到Files类
 *		2020.4.10   1.删除storeStreamToBAOS方法（不实用）
 * 				    2.优化序列化方法
 *		2020.4.12   1.将序列化方法转移到Objs类
 *				    2.添加readToStr方法
 * 
 */

package com.jmc.io;

import com.jmc.chatserver.*;
import java.io.*;
import java.util.*;
import com.jmc.lang.*;
import java.nio.charset.*;

public class Streams {
	public static byte[] read(InputStream in) {
		int i = 0;
		byte[] b = new byte[8192];
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			while ((i = in.read(b)) != -1) {
				out.write(b, 0, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseUtils.closeAll(in);
		}
		return out.toByteArray();
	}
	
	public static String readToStr(InputStream in, String charsetName) {
		return new String(read(in), Charset.forName(charsetName));
	}
	public static String readToStr(InputStream in) {
		return readToStr(in, Charset.defaultCharset().name());
	}
	
	public static void out(InputStream in, OutputStream out, boolean closeOut) {
		int i = 0;
		byte[] b = new byte[8192];
		try {
			while ((i = in.read(b)) != -1) {
				out.write(b, 0, i);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseUtils.closeAll(in);
			if (closeOut) CloseUtils.closeAll(out);
		}
	}
}
