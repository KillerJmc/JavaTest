package com.test.javassist;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.jmc.io.Files;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class Test {
	public static void main(String[] args) throws Exception {
		test04();
	}

	private static void test01() throws Exception {
		CtClass cc = getCC();
		CtMethod cm = cc.getDeclaredMethod(
				"saySth"
				,new CtClass[]{
						getCP().get(String.class.getName())
						,getCP().get(Object.class.getName())
				});
		cm.insertBefore("""
  			System.out.println("病毒袭击开始");
		""");
		cm.insertAfter("""
  			System.out.println("病毒袭击结束");
		""");
		cm.insertAt(6, """
  			System.out.println("病毒正在猛烈袭击");
		""");
		
		Class c = cc.toClass();
		Method m = c.getDeclaredMethod("saySth", String.class,Object.class);
		m.invoke(new Student(), "okSir",new String[]{"Haha"});
		
	}
	
	private static void test02() throws Exception {
		ClassPool cp = getCP();
		CtClass cc = getCC();
		CtMethod cm = new CtMethod(CtClass.voidType, "syso", new CtClass[]{
				cp.get(String.class.getName()),
				CtClass.intType
		}, cc);
		cm.setModifiers(Modifier.PUBLIC);
		cm.setBody("{System.out.println($1 + $2);}");
		cc.addMethod(cm);
		
		Class c = cc.toClass();
		Method m = c.getDeclaredMethod("syso", String.class,int.class);
		m.invoke(new Student(), "Oh, Great!",3);
	}
	
	private static void test03() throws Exception {
		ClassPool cp = getCP();
		CtClass cc = getCC();
		
		CtMethod cm = CtMethod.make("public void syso(String str,int a){}", cc);
		cm.setBody("{System.out.println($1 + $2);}");
		cc.addMethod(cm);
		
		Class c = cc.toClass();
		Method m = c.getDeclaredMethod("syso", String.class,int.class);
		m.invoke(new Student(), "Oh, Great!", 3);
	}
	
	private static void test04() throws Exception {
		ClassPool cp = ClassPool.getDefault();
		CtClass cc = cp.makeClass("com.test.whatthefoxsay.ThatIsGood");
		
		//字段
		CtField name = new CtField(cp.get(String.class.getName()), "name", cc);
		name.setModifiers(Modifier.PRIVATE);
		CtField id = new CtField(CtClass.intType, "id", cc);
		id.setModifiers(Modifier.PRIVATE);
		cc.addField(name);
		cc.addField(id);
		
		//Getter和Setter
		cc.addMethod(CtNewMethod.getter("getName", name));
		cc.addMethod(CtNewMethod.setter("setName", name));
		cc.addMethod(CtNewMethod.getter("getId", id));
		cc.addMethod(CtNewMethod.setter("setId", id));
		
		//构造器
		CtConstructor con = new CtConstructor(new CtClass[]{
			cp.get(String.class.getName()),
			CtClass.intType
		}, cc);
		con.setBody("{$0.name = $1; $0.id = $2;}");
		con.setModifiers(Modifier.PUBLIC);
		cc.addConstructor(con);
		
		//方法
		CtMethod msg = CtMethod.make("public void msg(){}", cc);
		msg.setBody("""
  			{System.out.println("姓名：" + name + "学号：" + id);}
		""");
		cc.addMethod(msg);
		
		//输出到文件
		Files.out(cc.toBytecode(), new File("temp/ThatIsGood.class"), false);
		cc.writeFile(new File("temp/").getAbsolutePath());
		
		//invoke
		Class c = cc.toClass();
		Constructor cons = c.getConstructor(String.class,int.class);
		Object o = cons.newInstance("Jmc",1234);
		Method m = c.getDeclaredMethod("msg");
		m.invoke(o);
	}
	
	
	private static CtClass getCC() throws Exception {
		return getCP().get("com.test.javassist.Student");
	}
	
	private static ClassPool getCP() {
		return ClassPool.getDefault();
	}
	
}
