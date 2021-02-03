package com.jmc.lang.extend;

/**
 * Byte拓展类
 */
public class Bytes {
    public static void xor(byte[] src, byte key) {
        for (int i = 0; i < src.length; i++) src[i] ^= key;
    }
}
