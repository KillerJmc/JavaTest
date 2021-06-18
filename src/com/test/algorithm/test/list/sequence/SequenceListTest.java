package com.test.algorithm.test.list.sequence;

import com.jmc.lang.reflect.Reflects;
import com.test.algorithm.list.sequence.impl.SequenceList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SequenceListTest {
    @Test
    public void test1() {
        var l = new SequenceList<String>(10);
        l.insert("Jmc");
        l.insert("Ruby");
        l.insert("Merry");
        System.out.println(Arrays.toString(l.getEles()));

        System.out.println(l.indexOf("Merry"));
        System.out.println(l.indexOf("Hello"));

        l.insert(1, "OKSir");
        System.out.println(Arrays.toString(l.getEles()));
        System.out.println("-----------------------------");
        for (var s : l) {
            System.out.print(s + " ");
        }
        System.out.println("\n-----------------------------");

        String s = l.get(1);
        System.out.println(s);

        String removeEle = l.remove(0);
        System.out.println(removeEle);

        System.out.println(Arrays.toString(l.getEles()));

        l.clear();
        System.out.println(l.size());
        System.out.println(Arrays.toString(l.getEles()));
    }

    @Test
    public void test2() {
        var l = new SequenceList<String>(3);
        System.out.println(l.capacity());
        l.insert("Jmc");
        System.out.println(l.capacity());
        l.insert("Ruby");
        System.out.println(l.capacity());
        l.insert("Merry");
        System.out.println(l.capacity());

        System.out.println("-----------------------------");

        l.insert("OKSir");
        System.out.println(l.capacity());

        System.out.println("-----------------------------");

        System.out.println(l.capacity());
        l.remove(0);
        System.out.println(l.capacity());
        l.remove(0);
        System.out.println(l.capacity());
        l.remove(0);
        System.out.println(l.capacity());

        System.out.println("-----------------------------");
        l.remove(0);
        System.out.println(l.capacity());

        System.out.println("-----------------------------");
    }

    @Test
    public void test3() throws Exception {
        var a = new ArrayList<Integer>();
        for (int i = 0; i < 11; i++) {
            if (i == 0) {
                var elementData = Reflects.getField(a, "elementData", Object[].class);
                System.out.println(elementData.length);
            }
            a.add(1);
            var elementData = Reflects.getField(a, "elementData", Object[].class);
            System.out.println(elementData.length);
        }

        for (int i = 0; i < 11; i++) {
            if (i == 0) {
                var elementData = Reflects.getField(a, "elementData", Object[].class);
                System.out.println(elementData.length);
            }
            a.remove(0);
            a.trimToSize();
            var elementData = Reflects.getField(a, "elementData", Object[].class);
            System.out.println(elementData.length);
        }
    }
}
