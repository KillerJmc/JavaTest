package com.test.crypt;

public class Test {
	public static void main(String[] args) throws Exception {
		theory();
		encrypt();
		decrypt();
	}

	private static void theory() {
		int a = 3; //00000011 -> 11111111(0xff) -(相乘达到取反)-> 11111100
		//相同变为0，相反变为1
		System.out.println(Integer.toBinaryString(a ^ 0xff));
	}
	
	public static void encrypt() {
		EncryptUtil.encrypt("C:/Jmc/Temp/ClassLoaderTest/com/a/c/HellWorld.class",
							"C:/Jmc/Temp/ClassEncryptTest/com/a/c/HellWorld.class");
	}
	
	public static void decrypt() throws Exception {
		DecryptUtil decrypt = new DecryptUtil("C:/Jmc/Temp/ClassEncryptTest");
		Class<?> c = decrypt.loadClass("com.a.c.HellWorld");
	}
}
