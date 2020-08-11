package com.test.main;

import com.jmc.decompile.ClassDecompile;
import com.jmc.io.Files;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Tools {
	public interface RunnableThrowsException {
		void run() throws Exception;
	}
	public interface RunnableThrowsAndReturn<T> {
		T run() throws Exception;
	}

	public static String getJavaPath(Class<?> c) {
		return "src/"
			   + c.getName().replace(".", "/")
			   + ".java";
	}

	public static void writeFileToDesktop(byte[] b, String fileName) {
		Files.out(b, "C:/Users/Jmc/Desktop/" + fileName, true);
	}
	public static void writeFileToDesktop(String content, String fileName) {
		writeFileToDesktop(content.getBytes(), fileName);
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
		copyToDesktop(getJavaPath(c));
	}
	
	public static void copyClassToDesktop(Class<?> c) {
		copyToDesktop(Files.findAny(".", c.getSimpleName() + ".class"));
	}

	public static String getDesktopFilePath(String fileName) {
		return "C:/Users/Jmc/Desktop/" + fileName;
	}
	
	public static String readFromDesktop(String fileName) {
		return Files.read(getDesktopFilePath(fileName));
	}

	/**
	 * get Java Dir Path
	 * Copied from com.test.Main.Tools
	 */
	public static String getJavaDirPath(Class<?> c) {
		return "src/"
				+ c.getPackageName().replace(".", "/") + "/";
	}

	public static File getJavaParentFile(Class<?> c) {
		return new File(getJavaDirPath(c)).getParentFile();
	}

	public static String getJavaParentPath(Class<?> c) {
		return getJavaParentFile(c).getAbsolutePath() + "/";
	}

	public static String getFilePath(Class<?> c, String fileName) {
		return getJavaDirPath(c) + fileName;
	}
	
	public static String readFile(Class<?> c, String fileName) {
		return Files.read(getFilePath(c, fileName));
	}

	public static void pushToFile(String src, Class<?> c, String fileName, boolean appendMode) {
		Files.out(src, Tools.getFilePath(c, fileName), appendMode);
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
		String path = getJavaDirPath(c) + UML_Name + ".puml";
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
		String path = Tools.getJavaDirPath(c);
		List<File> fs = Files.findFiles(path, (f) -> f.getName().endsWith(".java"));
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

	public static void tryThis(RunnableThrowsException r) {
		try {
			r.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static<T> T tryAndReturn(RunnableThrowsAndReturn<T> r) {
		try {
			return r.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
