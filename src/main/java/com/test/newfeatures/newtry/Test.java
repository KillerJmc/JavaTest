package com.test.newfeatures.newtry;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 注：ois和oos需要是关闭的流
 * 	  而bis和bos不需要关
 * @author Jmc
 *
 */

public class Test {
	public static void main(String[] args) {
		previous();
		nowadays();
	}

	private static void previous() {
		FileInputStream in = null;
		FileOutputStream out = null;
		byte[] b = new byte[8192];
		
		try {
			in = new FileInputStream("");
			out = new FileOutputStream("");
			int i = 0;
			while ((i = in.read(b)) != -1) {
				out.write(b,0,i);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
				in.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}		
	}

	/**
	 * try会自动关闭(释放)小括号里面的资源
	 * try-with-resource是java7的新特性
	 *    可用于代替finally
	 */
	private static void nowadays() {
		try (var in = new FileInputStream("");
			 var out = new FileOutputStream("")) {
			in.transferTo(out);
			out.flush();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
