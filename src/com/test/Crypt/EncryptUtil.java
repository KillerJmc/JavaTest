package com.test.Crypt;

import com.jmc.io.Files;

public class EncryptUtil {
	public static void encrypt(String src, String des) {
		byte[] in = Files.readToBytes(src);
		for (int i = 0; i < in.length; i++) {
			in[i] ^= 0xff; 
		}
		Files.outBytes(in, des, false);
	}
}
