package com.test.classloader.former;

import com.jmc.io.Files;

public class FileSystemClassLoader extends ClassLoader {
	private String rootDir;

	public FileSystemClassLoader(String rootDir) {
		this.rootDir = rootDir;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> c = findLoadedClass(name);
		if (c != null) {
			return c;
		} else {
			c = this.getParent().loadClass(name);
			if (c != null) {
				return c;
			} else {
				byte[] classData = getClassData(name);

				if (classData != null) {
					c = defineClass(name, classData, 0, classData.length);
				}
			}
		}
		return c;
	}
	
	/**
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return findClass(name);
	}
	*/

	public byte[] getClassData(String name) {
		return Files.readToBytes(rootDir + "/" + name.replace(".", "/") + ".class");
	}
}






