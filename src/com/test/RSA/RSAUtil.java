/**
 * RSA工具类
 * Author: Jmc
 */

package com.test.RSA;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jmc.io.Files;

public class RSAUtil {
	//公钥
	private static BigInteger e;
	//私钥
	private static BigInteger d;
	//公共模数
	private static BigInteger n;
	//fhiN
	private static BigInteger fhiN;
	//存放集合(线程安全)
	public static ConcurrentHashMap<Integer, String> map;
	public static ConcurrentHashMap<Integer, Byte> map2;
	//线程池
	public static ExecutorService pool;
	
	static {
		map = new ConcurrentHashMap<Integer, String>();
		map2 = new ConcurrentHashMap<Integer, Byte>();
		Files.basicDetails = false;
	}
	
	private RSAUtil() {
		prepare();
	}
	
	/**
	 * 单例模式
	 */
	private static class RSAUtilInstance {
		private static final RSAUtil instance = new RSAUtil();
	}
	
	public static RSAUtil getInstance() {
		return RSAUtilInstance.instance;
	}
	
	/**
	 * 准备
	 */
	public void prepare() {
		Random rand = new Random(System.currentTimeMillis());
		//密钥3072bit 加密程度128
		BigInteger p = BigInteger.probablePrime(1536, rand);
		BigInteger q = BigInteger.probablePrime(1536, rand);
		n = p.multiply(q);
		//fhiN = (p - 1) * (q - 1)
		fhiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		e = new BigInteger("65537");
		//测试数x
		BigInteger x = null;
		
		//ed % fhiN = 1 --> ed / fhiN = x...1 --> d = (xfhiN + 1) / e
		for (Integer i = 0; i < Integer.MAX_VALUE; i++) {
			x = new BigInteger(i.toString());
			BigInteger xFhiNAddOne = x.multiply(fhiN).add(BigInteger.ONE);
			if(xFhiNAddOne.mod(e).toString().equals("0")) {
				d = xFhiNAddOne.divide(e);
				if (d.toString().length() == 925) break;
			}
		}
		
		//销毁p和q
		p = null;
		q = null;
	}
	
	public String getPublicKey() {
		return e.toString();
	}

	public String getPrivateKey() {
		return d.toString();
	}

	public String getN() {
		return n.toString();
	}
	
	/**
	 * 加密
	 * @param b
	 * @param PUBLIC_KEY
	 * @param nStr
	 * @return The text after being encrypted;
	 */
	public static byte[] encrypt(byte[] b, String PUBLIC_KEY, String nStr) {
		BigInteger e = new BigInteger(PUBLIC_KEY);
		BigInteger n = new BigInteger(nStr);
		pool = Executors.newFixedThreadPool(10);
		
		for (int i = 0; i < b.length; i++) {
			BigInteger byteBigInteger = new BigInteger(new Byte(b[i]).toString());
			//解决线程final问题
			int key = i;
			pool.execute(() -> {
				//移除modPow方法(仅返回正数，导致加密异常)
				map.put(key,
						byteBigInteger.pow(e.intValueExact()).remainder(n).toString()
				);
			});
		}	
		pool.shutdown();
		
		String des = strs2Str(mapToStrs(b.length));
		return encryptedResultHandler(des);
	}
	public static boolean encrypt(byte[] b, String PUBLIC_KEY, String nStr, String desPath) {
		return Files.outBytes(encrypt(b, PUBLIC_KEY, nStr), desPath, false);
	}
	public static boolean encrypt(byte[] b, String PUBLIC_KEY, String nStr, File des) {
		return encrypt(b, PUBLIC_KEY, nStr, des.getAbsolutePath());
	}
	
	public byte[] encrypt(byte[] b) {
		return encrypt(b, getPublicKey(), getN());
	}
	public boolean encrypt(byte[] b, String desPath) {
		return Files.outBytes(encrypt(b), desPath, false);
	}
	public boolean encrypt(byte[] b, File des) {
		return encrypt(b, des.getAbsolutePath());
	}
	
	/**
	 * 解密
	 * @param b
	 * @param PRIVATE_KEY
	 * @param nStr
	 * @return The text after being decrypted
	 */
	public static byte[] decrypt(byte[] b, String PRIVATE_KEY, String nStr) {
		String src = decryptedInitialBytesHandler(b);
		String[] srcStrs = src.split(" ");
		
		BigInteger d = new BigInteger(PRIVATE_KEY);
		BigInteger n = new BigInteger(nStr);
		
		pool = Executors.newFixedThreadPool(10);
		
		for (int i = 0; i < srcStrs.length; i++) {
			map.put(i, srcStrs[i]);
		}
		
		for (int i = 0; i < srcStrs.length; i++) {
			int key = i;
			String value = map.get(i);
			BigInteger strBigInteger = new BigInteger(value);
			
			if (value.startsWith("-")) {
				pool.execute(() -> {
					map2.put(key,strBigInteger.negate().modPow(d, n).negate().byteValueExact());
				});
			} else {
				pool.execute(() -> {
					map2.put(key,strBigInteger.modPow(d, n).byteValueExact());
				});
			}
		}
		pool.shutdown();
		
		return mapToBytes(srcStrs.length);
	}	
	public static boolean decrypt(byte[] b, String PRIVATE_KEY, String nStr, String desPath) {
		return Files.outBytes(decrypt(b, PRIVATE_KEY, nStr), desPath, false);
	}
	public static boolean decrypt(byte[] b, String PRIVATE_KEY, String nStr, File des) {
		return decrypt(b, PRIVATE_KEY, nStr, des.getAbsolutePath());
	}
	
	public byte[] decrypt(byte[] b) {
		return decrypt(b, getPrivateKey(), getN());
	}
	public boolean decrypt(byte[] b, String desPath) {
		return Files.outBytes(b, desPath, false);
	}
	public boolean decrypt(byte[] b, File des) {
		return decrypt(b, des.getAbsolutePath());
	}
	
	/**
	 * 把map转换成字符串
	 * @param length
	 * @return String[]
	 */
	private static String[] mapToStrs(int length) {
		String[] desStrs = new String[length];
		
		while (!pool.isTerminated()) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				return null;
			}
		}	

		for (int i = 0; i < length; i++) {
			desStrs[i] = map.get(i);
		}
		
		//清除map
		map.clear();
		
		return desStrs;
	}
	
	/**
	 * 把map转换成byte
	 * @param length
	 * @return byte[]
	 */
	private static byte[] mapToBytes(int length) {
		while (!pool.isTerminated()) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				return null;
			}
		}	
		
		byte[] desBytes = new byte[length];
		for (int i = 0; i < length; i++) {
			desBytes[i] = map2.get(i);
		}
		
		map.clear();
		map2.clear();
		
		return desBytes;
	}
	
	/**
	 * 拼接字符串
	 * @param strs
	 * @return String
	 */
	private static String strs2Str(String[] strs) {
		StringBuilder sb = new StringBuilder();
		
		for (String str : strs) {
			sb.append(str).append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 加密结果处理器
	 * <p>对加密结果进行压缩和异或处理
	 * @param src
	 * @return String
	 */
	private static byte[] encryptedResultHandler(String src) {
		File des = System.getProperty("os.name").contains("Windows") ? 
				   new File("C://RSA/rsa.tmp") :
			       new File("/sdcard/RSA/rsa.tmp");
			
		Files.out(src, des, false);
		String zipPath = des.getParent() + "/rsaEncryption.zip";
		Files.zip(des, zipPath);
		
		byte[] b = Files.readToBytes(zipPath);
		Files.delete(des.getParent());
		
		for (int i = 0; i < b.length; i++) {
			b[i] ^= 10;
		}
		return b;
	}
	
	/**
	 * 解密初始字串符处理器
	 * <p>对解密初始字串符进行解压和再异或处理
	 * @param b
	 * @return String
	 */
	 private static String decryptedInitialBytesHandler(byte[] b) {
		 File des = System.getProperty("os.name").contains("Windows") ? 
					new File("C://RSA/rsaDectyption.zip") :
					new File("/sdcard/RSA/rsaDectyption.zip");
					
		 for (int i = 0; i < b.length; i++) {
			 b[i] ^= 10;
		 }
		 
		 Files.outBytes(b, des, false);
		 Files.unzip(des);
		 
		 String desStr = Files.read(des.getParent() + "/rsa.tmp");
		 Files.delete(des.getParent());
		 
		 return desStr;
	 }
}
