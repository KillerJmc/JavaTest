package com.test.classloader.former;

import com.jmc.io.Files;
import com.test.crypt.DecryptUtil;

public class FileSystemClassLoader extends ClassLoader {
	private String rootDir;
	private boolean override;

	public FileSystemClassLoader(String rootDir, boolean override) {
		this.rootDir = rootDir;
		this.override = override;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> c = findLoadedClass(name);
		if (c != null) {
			return c;
		} else {
			try {
				c = this.getParent().loadClass(name);
			} catch (Exception e) {
				
			}
			if (c != null) {
				return c;
			} else {
				byte[] classData = null;
				if (!override) {
					classData = getClassData(name);
				} else {
					classData = DecryptUtil.getClassData(name);
				}
				
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






