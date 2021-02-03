package com.test.algorithm.test.hash;

import com.jmc.lang.extend.Outs;
import com.test.algorithm.hash.Emp;
import com.test.algorithm.hash.HashTab;
import org.junit.Test;

public class HashTabTest {
    @Test
    public void test() {
        var hashTab = new HashTab<Emp>(7);

        Emp e = new Emp(1, "Tom");
        Emp e2 = new Emp(2, "Jack");
        Emp e3 = new Emp(8, "Smith");

        hashTab.add(e.id(), e);
        hashTab.add(e2.id(), e2);
        hashTab.add(e3.id(), e3);

        System.out.println(hashTab);
        System.out.println(hashTab.findById(8));
        System.out.println(hashTab.findById(2));

        Outs.newLine();

        hashTab.deleteById(2);
        System.out.println(hashTab);
    }
}
