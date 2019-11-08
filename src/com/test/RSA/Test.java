package com.test.RSA;

import com.jmc.io.Files;


public class Test {

	public static String encrypt;
	
	public static void main(String[] args) throws Exception {		
		RSAUtil rsa = RSAUtil.getInstance();
		String PUBLIC_KEY = rsa.getPublicKey();
		String PRIVATE_KEY = rsa.getPrivateKey();
		String nStr = rsa.getN();
		
		System.out.println(PUBLIC_KEY);
		System.out.println(PRIVATE_KEY.length());
		System.out.println(nStr.length());
		
		String src = Files.read("C:/Jmc/Eclipse/Workspace/JavaTest/src/com/test/RSA/RSAUtil.java");
		//Files.out(PRIVATE_KEY, "C://Jmc/PRIVATE_KEY.txt", false);
		
		timer(() -> {
			rsa.encrypt(src.getBytes(), "C:/Jmc/ok.txt");
		});
		timer(() -> {
			System.out.println(new String(rsa.decrypt(Files.readToBytes("C:/Jmc/ok.txt"))));
		});
		
	}
	
	public static interface Timer {
		public void run();
	}
	
	
	public static void timer(Timer t) {
		long startTime = System.currentTimeMillis();
		t.run();
		long endTime = System.currentTimeMillis();
		System.out.println("耗时" + (endTime - startTime) / 1000 + "秒");
	}
	
	
	
}
	