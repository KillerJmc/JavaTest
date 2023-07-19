package com.test.utils;

import java.util.Base64;

/**
 * Base64加解密
 */
public class Base64Utils {
    /**
     * 加密算法
     * @param bytes 字节数组
     * @return 加密后的字符串
     */
    public static String encrypt(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 解密算法
     * @param encryptedStr 加密后的字符串
     * @return 原字节数组
     */
    public static byte[] decrypt(String encryptedStr) {
        return Base64.getDecoder().decode(encryptedStr);
    }
}
