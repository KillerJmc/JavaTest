package com.test.rsa;

import com.jmc.io.Files;
import com.test.apply.rsa.RSA;
import org.junit.Test;

import java.util.Arrays;

public class RSATest {
    @Test
    public void test() {
        var keys = RSA.getKeys();
        var privateKey = keys.getPrivateKey();
        var publicKey = keys.getPublicKey();

        byte[] src = Files.readToBytes("temp/MollyFlower.mp3");

        byte[] encrypt = RSA.encrypt(src, publicKey);
        Files.out(encrypt, "temp/EncryptMollyFlower.mp3", false);

        byte[] decrypt = RSA.decrypt(encrypt, privateKey);
        Files.out(decrypt, "temp/DecryptMollyFlower.mp3", false);

        System.out.println(Arrays.equals(src, decrypt));
    }

    @Test
    public void test2() {
        int bitLength = 20;
        var keys = RSA.getKeys(bitLength);
        var publicKey = keys.getPublicKey();
        var privateKey = keys.getPrivateKey();

        var fuckPrivateKey = FuckRSA.decrypt(publicKey, bitLength);
        System.out.println(fuckPrivateKey.equals(privateKey));
    }
}
