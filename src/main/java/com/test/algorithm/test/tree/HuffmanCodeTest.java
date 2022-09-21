package com.test.algorithm.test.tree;


import com.jmc.io.Files;
import com.test.algorithm.tree.impl.HuffmanCode;
import org.junit.Test;

import java.util.Arrays;

public class HuffmanCodeTest {
    @Test
    public void test() {
        var content = "Jmc666";
        var bs = content.getBytes();
        System.out.println(Arrays.toString(bs));
        System.out.println(bs.length);

        var zip = HuffmanCode.zip(bs);
        System.out.println(Arrays.toString(zip.getFirst()));
        System.out.println(zip.getFirst().length);
        var unzip = HuffmanCode.unzip(zip);
        System.out.println(new String(unzip));
    }

    @Test
    public void test2() {
        var bs = Files.readToBytes("temp/head.jpg");
        System.out.println(bs.length);

        var zip = HuffmanCode.zip(bs);
        System.out.println(zip.getFirst().length);
        Files.out(zip.getFirst(), "temp/head.jzip", false);

        var unzip = HuffmanCode.unzip(zip);
        System.out.println(unzip.length);
        Files.out(unzip, "temp/head_unzip.jpg", false);
    }
}