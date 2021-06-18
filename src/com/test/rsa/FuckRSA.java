package com.test.rsa;

import com.jmc.lang.math.ExactExp;
import com.jmc.reference.Func;
import com.test.apply.utils.rsa.RSA;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * 简单的破解RSA类
 */
public class FuckRSA {
    /**
     * 推出公钥中n的两个因数：p和q <br>
     * 考虑到运算速度，本方法要求p和q的比特长度相同且小于等于20
     * @param publicKey 公钥
     * @param bitLength p或q的比特长度
     */
    public static RSA.PrivateKey decrypt(RSA.PublicKey publicKey, int bitLength) {
        if (bitLength > 20) throw new IllegalArgumentException("bitLength > 20，运算速度慢。");

        var n = publicKey.getN();
        var e = publicKey.getE().intValueExact();

        // 求出区间内的所有质数
        var primes = new ArrayList<BigInteger>();

        for (var t = BigInteger.ONE.shiftLeft(bitLength - 1).nextProbablePrime();
             t.shiftRight(bitLength).equals(BigInteger.ZERO);
             t = t.nextProbablePrime()) {
            primes.add(t);
        }

        for (var p : primes) {
            for (var q : primes) {
                var res = p.multiply(q);

                // 如果res超出范围
                if (res.subtract(n).signum() == 1) {
                    break;
                }

                if (res.equals(n)) {
                    var phiN = ExactExp.getResult("(%d - 1) * (%d - 1)".formatted(p, q)).toBigIntegerExact();

                    var modMul = Func.of((Integer a, char[] cs, Integer c) -> {
                        int result = 0;

                        int b = cs.length - 1;
                        while (b >= 0) {
                            if (cs[b] == '1') {
                                result = (result + a) % c;
                            }
                            a = a % c * 2 % c;
                            b--;
                        }
                        return result;
                    });

                    int k = 0;
                    while ((modMul.invoke(++k, phiN.toString(2).toCharArray(), e) + 1) % e != 0);
                    var d = ExactExp.getResult("(%d * %d + 1) / %d".formatted(k, phiN, e)).toBigIntegerExact();

                    return new RSA.PrivateKey(n, d);
                }
            }
        }
        return new RSA.PrivateKey(n, BigInteger.ZERO);
    }
}
