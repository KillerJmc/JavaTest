/**
 * ls -l 权限转换
 * 
 * @author Jmc
 * 
 */
package com.test.Apply.Linux;

import com.test.Main.Tools;

public class TestLL {
	public static void main(String[] args) {
		test();
	}
	
	private static void test() {	
		String src = Tools.readFile(TestLL.class, "test.txt");
		int index = src.indexOf("\n");
		
		System.out.println("原来：\n" + src);
		System.out.println("现在：");
		
		String firstLine = src.substring(0, index);
		System.out.println(firstLine);
		
		src.substring(index + 1)
		   .lines()
		   .map((l) -> permissionTrans(l.substring(1,10)) + l.substring(10))
		   .forEach(System.out::println);
	}
	
	public static String permissionTrans(String session) {
		int k = 0; 
		String result = "";
		byte[] bs = session.getBytes();
		
		for (int i = 0; i < bs.length; i++) {
			switch (bs[i]) {
				case 'r' :
					k += 4;
					break;
				case 'w' :
					k += 2;
					break;
				case 'x' :
					k += 1;
					break;
			}
			if ((i + 1) % 3 == 0) {
				result += k;
				k = 0;
			}
		}
		return result;
	}
}








