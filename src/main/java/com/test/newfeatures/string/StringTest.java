package com.test.newfeatures.string;

import com.jmc.io.Files;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.stream.Stream;

public class StringTest {
	public static void main(String[] args) throws Exception {
		test01();
		test02();
		test03();
	}

	private static void test01() {
		var str = "\r \n \t";
		//判断空白
		System.out.println(str.isBlank());
		
		//去除首尾空白
		//效果一样，但新版strip更智能（识别多种语言的空格符号）
		str = "\n Hello \n";
		var str2 = str.strip();
		System.out.println(str2);
		System.out.println(str2.length());
		var str3 = str.trim();
		System.out.println(str3);
		System.out.println(str3.length());
		
		str = "   Hello   ";
		
		//去除首部空白
		var str4 = str.stripLeading();
		System.out.println(str4);
		System.out.println(str4.length());
		
		//去除尾部空白
		var str5 = str.stripTrailing();
		System.out.println(str5);
		System.out.println(str5.length());
		
		System.out.println("--------------------------------------------------");
	}
	
	private static void test02() {
		//复制字符串
		var str = "Java";
		System.out.println(str.repeat(5));
		
		//获取当前文件路径
		var path = "src/"
				   + StringTest.class.getName().replace(".", "/")
				   + ".java";
		
		//lines方法获得字符串行的流，可以对行进行处理
		var str2 = Files.read(path);
		Stream<String> stream = str2.lines();
		stream.filter((t) -> t.contains("处理"))
			  .map(String::strip)
			  .forEach(System.out::println);
		
		//流已被销毁，要重新赋值
		stream = str2.lines();
		System.out.println(stream.count());
		System.out.println("--------------------------------------------------");
	}
	
	private static void test03() throws Exception {
		//1.fis新增一个available方法，可以获得可用字节数来给数组b（相当于f.length())
		/**
		 * var b = new byte[new FileInputStream("").available()];
		 */
		
		//2. transferTo方法
		File src = new File("src/"
				   + StringTest.class.getName().replace(".", "/")
				   + ".java");
		var out = new ByteArrayOutputStream();
		int length;
		try (var in = new FileInputStream(src)) {
			length = in.available();
			in.transferTo(out);
		}
		System.out.println(
			out.toByteArray().length == length
		);
	}
}
