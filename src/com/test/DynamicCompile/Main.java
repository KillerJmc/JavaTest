package com.test.DynamicCompile;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import com.test.Main.Tools;

public class Main {

	public static void main(String[] args) throws Exception {
		String path = Tools.getJavaFilePath(HelloWorld.class);
		compile(path);
		compile1();
	}

	private static void compile(String path) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int result = compiler.run(null, null, null, path);
		System.out.println(result == 0? "编译成功" : "编译失败");
	}
	
	@SuppressWarnings("all")
	private static void compile1() throws Exception {
		Class c = HelloWorld.class;
		c.getMethod("main", String[].class)
			.invoke(null, (Object)new String[]{});
	}

}













