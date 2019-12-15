package com.test.classloader;

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
		System.out.println("鍚嶇О锛�" + name);
		if (c != null) {
			System.out.println("\n绫诲凡鍔犺浇锛�");
			newLine();
			return c;
		} else {
			System.out.println("\n鏈鍔犺浇");
			
			try {
				c = this.getParent().loadClass(name);
			} catch (Exception e) {
				
			}
			if (c != null) {
				System.out.println("榛樿绫诲姞杞藉櫒鎴愬姛浜�!");
				newLine();
				return c;
			} else {
				System.out.println("榛樿绫诲姞杞藉櫒鎸備簡");
				//姝ゆ柟娉曚互涓婁唬鐮佸潎鍙笉鍐�(loadClass鏂规硶涓凡瀛樺湪)
				byte[] classData = null;
				if (!override) {
					classData = getClassData(name);
				} else {
					classData = DecryptUtil.getClassData(name);
				}
				
				if (classData != null) {
					c = defineClass(name, classData, 0, classData.length);
					System.out.println("杈撳叆娴佽儨鍒╋紒");
					newLine();
				} else {
					System.out.println("绫绘病鎵惧埌");
					newLine();
				}
			}
		}
		return c;
	}
	
	//鐮村潖鍙屼翰濮旀墭鏈哄埗(鍙閲嶅啓杩欎釜鏂规硶锛岀敤涓嶅悓鏂瑰紡璋冪敤鍧囧彲瀹炵幇鐮村潖鏈哄埗)
	//鐢变簬鏈哄埗琚牬鍧忥紝Object.class涔熼渶瑕佹垜浠皟鐢紝鍚﹀垯浼氭姤閿�
	/**
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return findClass(name);
	}
	*/

	public byte[] getClassData(String name) {
		return Files.readToBytes(rootDir + "/" + name.replace(".", "/") + ".class");
	}
	
	private void newLine() {
		System.out.println("---------------------------------------------------------------------------");
	}
}






