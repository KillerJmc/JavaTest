package com.test.apply.utils.rsa;

import com.jmc.lang.extend.Tries;
import com.jmc.lang.math.ExactExp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Random;

/**
 * RSA算法
 *
 * <pre>
 *
 * (n, e)：公钥    (n, d)：私钥     m：明文     c：密文
 *
 * c = m ** e % n
 * m = c ** d % n
 *
 * n = p * q(p, q ∈ PRIM)                -> n
 *
 * φ(n) = (p - 1) * (q - 1)
 *
 * e ∈ (0, φ(n)) ∈ PRIM               -> e
 *
 * e * d % φ(n) = 1                   -> d
 * </pre>
 *
 * @author Jmc
 * @date 2021.5.26
 */
public class RSA {
    /**
     * 公钥，私钥总类
     */
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @SuppressWarnings("all")
    public static class Keys {
        private PublicKey publicKey;
        private PrivateKey privateKey;
    }

    /**
     * 公钥类
     */
    @Data
    public static class PublicKey {
        private BigInteger n;
        private BigInteger e;

        public PublicKey(BigInteger n, BigInteger e) {
            this.n = n;
            this.e = e;
        }
    }

    /**
     * 私钥类
     */
    @Data
    public static class PrivateKey {
        private BigInteger n;
        private BigInteger d;

        public PrivateKey(BigInteger n, BigInteger d) {
            this.n = n;
            this.d = d;
        }
    }

    /**
     * 默认p与q的二进制比特位数
     */
    private static final int DEFAULT_BIT_LENGTH = 1024;

    /**
     * 加密后的byte数组的开始标志
     */
    private static final byte[] START_SYMBOL = new byte[] {0, 0, 0};

    /**
     * 加密后的byte数组的结束标志
     */
    private static final byte[] END_SYMBOL = new byte[] {0, 0, 0};

    /**
     * 加密后的byte数组的分块标志
     */
    private static final byte[] SPLIT_SYMBOL = new byte[] {1, 2, 3};

    /**
     * 前导0的标志位（不同语言对负数模运算存在争议，因此均转为正数进行计算）
     */
    private static final byte LEADING_ZERO_SYMBOL = 127;


    /**
     * 获取公钥和私钥
     * @return 公钥和私钥
     */
    public static Keys getKeys() {
        return getKeys(DEFAULT_BIT_LENGTH);
    }

    /**
     * 通过p与q的二进制比特位数获取公钥和私钥
     * @param bitLength p与q的二进制比特位数
     * @return 公钥和私钥
     */
    public static Keys getKeys(int bitLength) {
        if (bitLength < 20) throw new IllegalArgumentException("bitLength < 20");

        Random rnd = new Random(System.currentTimeMillis());
        int e = 65537;
        var p = BigInteger.probablePrime(bitLength, rnd);
        var q = BigInteger.probablePrime(bitLength, rnd);
        var n = p.multiply(q);
        var phiN = ExactExp.getResult("(%d - 1) * (%d - 1)".formatted(p, q)).toBigIntegerExact();

        char[] csPhiN = phiN.toString(2).toCharArray();
        int k = 0;
        // 找到使得(k * φ(n) + 1) % e = 0的k值
        while ((modMul(++k, csPhiN, e) + 1) % e != 0);

        // d = (k * φ(n) + 1) / e
        var d = ExactExp.getResult("(%d * %d + 1) / %d".formatted(k, phiN, e)).toBigIntegerExact();

        return new Keys(new PublicKey(n, BigInteger.valueOf(e)), new PrivateKey(n, d));
    }

    /**
     * 通过公钥加密
     * @param src 待加密的byte数组
     * @param publicKey 公钥
     * @return 加密完成的byte数组
     */
    public static byte[] encrypt(byte[] src, PublicKey publicKey) {
        var e = publicKey.getE();
        var n = publicKey.getN();
        // 加密块大小（为n对应byte数组长度-1）
        final int BLOCK_SIZE = n.bitLength() / 8 - 1;

        var bis = new ByteArrayInputStream(src);
        var bos = new ByteArrayOutputStream();

        Tries.tryThis(() -> {
            bos.write(START_SYMBOL);

            // 包含前导0（不同语言对负数模运算存在争议，因此均转为正数进行计算）
            byte[] buff = new byte[BLOCK_SIZE + 1];
            int len;

            while ((len = bis.read(buff, 1, BLOCK_SIZE)) != -1) {
                // 前导0的标志位
                buff[0] = LEADING_ZERO_SYMBOL;
                var m = new BigInteger(buff, 0, len + 1);
                bos.write(m.modPow(e, n).toByteArray());
                bos.write(SPLIT_SYMBOL);
            }

            bos.write(END_SYMBOL);
        });

        return bos.toByteArray();
    }

    /**
     * 通过私钥解密
     * @param src 待解密的byte数组
     * @param privateKey 私钥
     * @return 解密完成的byte数组
     */
    public static byte[] decrypt(byte[] src, PrivateKey privateKey) {
        checkIntegrity(src);
        var d = privateKey.getD();
        var n = privateKey.getN();

        // 去除首尾完整性标识
        var bis = new ByteArrayInputStream(src, START_SYMBOL.length, src.length - START_SYMBOL.length - END_SYMBOL.length);
        var buff = new ByteArrayOutputStream();
        var res = new ByteArrayOutputStream();

        Tries.tryThis(() -> {
            int i;
            loop: while ((i = bis.read()) != -1) {
                if (i == SPLIT_SYMBOL[0]) {
                    // 记录当前位置（防止分隔符多读或者少读引起的冲突）
                    bis.mark(-1);

                    byte[] bs = bis.readNBytes(SPLIT_SYMBOL.length - 1);
                    for (int t = 0; t < bs.length; t++) {
                        if (bs[t] != SPLIT_SYMBOL[t + 1]) {
                            buff.write(i);
                            // 回到记录的mark位置
                            bis.reset();
                            continue loop;
                        }
                    }

                    byte[] c = buff.toByteArray();
                    byte[] m = new BigInteger(c).modPow(d, n).toByteArray();
                    // 去除前导0
                    if (m[0] == LEADING_ZERO_SYMBOL) {
                        res.write(m, 1 , m.length - 1);
                    } else {
                        res.write(m);
                    }
                    buff.reset();
                } else {
                    buff.write(i);
                }
            }
        });

        return res.toByteArray();
    }

    /**
     * 检查加密过的byte数组的完整性
     * @param encrypted 加密过的byte数组
     */
    private static void checkIntegrity(byte[] encrypted) {
        if (encrypted.length < START_SYMBOL.length + END_SYMBOL.length) {
            throw new IllegalArgumentException("输入的byte数组完整性校验不通过，解密失败!");
        }

        for (int i = 0; i < START_SYMBOL.length; i++) {
            if (encrypted[i] != START_SYMBOL[i]) {
                throw new IllegalArgumentException("输入的byte数组完整性校验不通过，解密失败!");
            }
        }

        for (int i = 0; i < END_SYMBOL.length; i++) {
            if (encrypted[encrypted.length - 1 - i] != END_SYMBOL[END_SYMBOL.length - 1 - i]) {
                throw new IllegalArgumentException("输入的byte数组完整性校验不通过，解密失败!");
            }
        }
    }

    /**
     * 求a * b % c
     * @param a 整数a
     * @param cs 一个大数的char数组
     * @param c 整数c
     * @return 结果整数
     */
    private static int modMul(int a, char[] cs, int c) {
        int res = 0;

        int b = cs.length - 1;
        while (b >= 0) {
            if (cs[b] == '1') {
                res = (res + a) % c;
            }
            a = ((a % c) << 1) % c;
            b--;
        }
        return res;
    }
}
