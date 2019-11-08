package com.test.Main;

import com.jmc.decompile.ClassDecompile;
import com.jmc.io.Files;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class Tools {
	public interface RunnableThrowsException {
		void run() throws Exception;
	}
	public static String getJavaFilePath(Class<?> c) {
		return "src/"
			   + c.getName().replace(".", "/")
			   + ".java";
	}

	public static void writeFileToDesktop(byte[] b, String name) {
		Files.outBytes(b, "C:/Users/Jmc/Desktop/" + name, true);
	}
	public static void writeFileToDesktop(String content, String name) {
		writeFileToDesktop(content.getBytes(), name);
	}

	public static void copyToDesktop(File src) {
		Files.basicDetails = false;
		Files.copy(src, "C:/Users/Jmc/Desktop");
		Files.basicDetails = true;
	}
	
	public static void copyToDesktop(String path) {
		copyToDesktop(new File(path));
	}
	
	public static void copyJavaToDesktop(Class<?> c) {
		copyToDesktop(getJavaFilePath(c));
	}
	
	public static void copyClassToDesktop(Class<?> c) {
		copyToDesktop(Files.findAny(".", c.getSimpleName() + ".class"));
	}
	
	public static String readFromDesktop(String name) {
		return Files.read("C:/Users/Jmc/Desktop/" + name);
	}

	public static String readHerePath(Class<?> c) {
		return "src/"
				+ c.getPackageName().replace(".", "/") + "/";
	}

	public static String readFromHere(Class<?> c, String fileName) {
		return Files.read(readHerePath(c) + fileName);
	}
	
	public static void backupToExtra() {
		Files.basicDetails = false;
		String root = "D:/电脑/Directly/";
		if (!new File(root).exists()) {
			System.out.println("未检测到U盘！");
			return;
		}
		
		String oldPath = root + "Eclipse/Workspace/JavaTest";
		LocalDate createTime = Instant.ofEpochMilli(new File(oldPath).lastModified())
			.atOffset(ZoneOffset.ofHours(8)).toLocalDate();
		String zipPath = root + "Zips/JavaBackup/JavaTest_" + createTime + ".zip";
		if(Files.zip(oldPath, zipPath)) {
			Files.delete(oldPath);
		} else {
			System.out.println("创建zip失败！");
			return;
		}
		
		/**
		 * 最新的工程文件夹
		 */
		String srcPath = "C:/Jmc/Eclipse/Workspace/JavaTest";
		Files.copy(srcPath, root + "Eclipse/Workspace");
		
		System.out.println("Success!");
		Files.basicDetails = true;
	}
	
	public static void timer(RunnableThrowsException r) {
		long startTime = System.currentTimeMillis();
		try {r.run();} catch (Exception e) {e.printStackTrace();}
		long endTime = System.currentTimeMillis();
		System.out.println("耗时：" + (endTime - startTime) / 1000 + "秒");
	}
	
	public static void milliTimer(RunnableThrowsException r) {
		long startTime = System.currentTimeMillis();
		try {r.run();} catch (Exception e) {e.printStackTrace();}
		long endTime = System.currentTimeMillis();
		System.out.println("耗时：" + (endTime - startTime) + "毫秒");
	}
	
	public static void nanoTimer(RunnableThrowsException r) {
		long startTime = System.nanoTime();
		try {r.run();} catch (Exception e) {e.printStackTrace();}
		long endTime = System.nanoTime();
		System.out.println("耗时：" + (endTime - startTime) + "纳秒");
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void pushToUML(String UML_Name, Class<?> c) {
		StringBuilder sb = new StringBuilder();
		String path = readHerePath(c) + UML_Name + ".puml";
		sb.append("@startuml\n\n");
		for (String cName : getClasses(c)) {
			String str = null;
			try {
				str = ClassDecompile.decompile(cName);
			} catch (Exception e) {}
			sb.append(str.replace("public ", "+")
					.replace("private ", "-")
					.replace("protected ", "#")
					.replace(" {}", "")
					.replace("+class", "class")
					.replace("+interface", "interface")
					.replace("+static void main(String[] p1)", "{static} void main(args : String[])")
					.replace(";", "")
					.replace("\n\n", "\n"));
			sb.append("\n\n");
		}
		sb.append("@enduml");
		StringBuilder sb2 = new StringBuilder();
		sb.toString().lines()
					 .map((l) -> addAtLast(l, "void", "boolean", "List", "Object", "Map", "String", "double", "int"))
					 .forEach((l) -> sb2.append(l + "\n"));

		Files.out(sb2.toString(), path, true);
		System.out.println("Success!");
	}
	private static String addAtLast(String line, String... types) {
		for (String type : types) {
			if (line.contains(type)) {
				return line.replace(type + " ", "")
						+ " : " + type;
			}
		}
		return line;
	}
	private static List<String> getClasses(Class c) {
		String packageName = c.getPackage().getName();
		String path = Tools.readHerePath(c);
		List<File> fs = Files.findToMap(path, (f) -> true).get("file");
		return fs.stream()
				 .map((f) -> packageName + "." + f.getName().replace(".java", ""))
				 .collect(Collectors.toList());
	}

	public static void newLine() {
		System.out.println("--------------------------------------------------------------------------");
	}
	public static void shortNewLine() {
		System.out.println("------------------------------------");
	}
}
