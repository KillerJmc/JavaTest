package com.test.Crypt;

import com.jmc.io.Files;
import com.test.ClassLoader.FileSystemClassLoader;

public class DecryptUtil {
	public static String rootDir;
	
	public DecryptUtil(String rootDir) {
		this.rootDir = rootDir;
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		FileSystemClassLoader loader = new FileSystemClassLoader(rootDir,true);
		return loader.loadClass(name);
	}
	
	public static byte[] getClassData(String name) {
		byte[] b = Files.readToBytes(rootDir + "/" + name.replace(".", "/") + ".class");
		for (int i = 0; i < b.length; i++) {
			b[i] ^= 0xff;
		}
		return b;
	}
}
