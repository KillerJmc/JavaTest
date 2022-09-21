package com.test.rhino;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

public class Main {
	
	@SuppressWarnings("all")
	public static void main(String[] args) throws Exception {
		//获取脚本引擎管理器进而获取js引擎
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
				
		//直接执行字符串
		String str = "var a = 3;print(a);";
		engine.eval(str);
		System.out.println("-----------------------------------------");
		
		//定义变量，储存到引擎上下文中
		engine.put("i", "3000");
		//修改变量
		engine.eval("i = 300");
		System.out.println(engine.get("i"));
		System.out.println("-----------------------------------------");
		
		//定义函数
		String func = "function sum(a,b){return a + b}";
		engine.eval(func);
		//取得调用接口
		Invocable jsInvoke = (Invocable) engine;
		//执行脚本中定义的方法
		Object o = jsInvoke.invokeFunction("sum", 10,20);
		System.out.println(o);
		System.out.println("-----------------------------------------");
		
		//导入其他的包，使用其他包中的java类
		engine.eval("""
  			var x = java.util.Arrays.asList("云霄一中","将军山学校","元光小学")"
  		""");
		List<String> list = (List<String>) engine.get("x"); 
		for (String temp : list) {
			System.out.println(temp);
		}
		System.out.println("-----------------------------------------");

		//执行一个js文件
		URL url = Main.class.getClassLoader().getResource("com/test/rhino/a.js");
		FileReader reader = new FileReader(url.getPath());
		engine.eval(reader);
		reader.close();
	}
	
}
