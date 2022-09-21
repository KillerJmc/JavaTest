package com.test.algorithm.test.list.linked;

import com.jmc.util.Timers;
import com.test.algorithm.list.linked.impl.DoublyLinkedList;
import com.test.algorithm.list.sequence.impl.SequenceList;
import org.junit.Test;

import static com.jmc.lang.Outs.newLine;

public class DoublyLinkedListTest {
    public void print(DoublyLinkedList<?> l) {
        System.out.print("[");
        l.forEach(o -> System.out.print(o + ", "));
        System.out.print("\b\b]\n");
    }
    
    @Test
    public void test1() {
        var l = new DoublyLinkedList<String>();
        l.insert("Jmc");
        l.insert("Ruby");
        l.insert("Merry");
        print(l);

        System.out.println(l.indexOf("Merry"));
        System.out.println(l.indexOf("Hello"));

        l.insert(1, "OKSir");
        l.insert(l.size() - 1, "Haha");
        print(l);

        l.insert(1, "666");
        print(l);
        System.out.println("-----------------------------");
        for (var s : l) {
            System.out.print(s + " ");
        }
        System.out.println("\n");
        System.out.println(l.size());
        System.out.println("-----------------------------");

        String s = l.get(1);
        System.out.println(s);

        String removeEle = l.remove(0);
        System.out.println(removeEle);

        String removeEle2 = l.remove(l.size() - 1);
        System.out.println(removeEle2);

        print(l);

        l.clear();
        System.out.println(l.size());
        print(l);
    }

    @Test
    public void test2() {
        var a1 = new SequenceList<Integer>(10);
        var a2 = new DoublyLinkedList<Integer>();
        final int N = 1000000;
        Timers.milliTimer(() -> {
            for (int i = 0; i < N * 10; i++) {
                a1.insert(i);
            }
        }, "a1尾插入");
        Timers.milliTimer(() -> {
            for (int i = 0; i < N * 10; i++) {
                a2.insert(i);
            }
        }, "a2尾插入");

        newLine();

        Timers.milliTimer(() -> {
            for (int i = 0; i < N / 1000; i++) {
                a1.insert(0, i);
            }
        }, "a1头插入");
        Timers.milliTimer(() -> {
            for (int i = 0; i < N / 1000; i++) {
                a2.insert(0, i);
            }
        }, "a2头插入");

        newLine();

        Timers.milliTimer(() -> {
            for (int i = 0; i < N / 1000; i++) {
                a1.insert(a1.size() >> 1, i);
            }
        }, "a1中间插入");
        Timers.milliTimer(() -> {
            for (int i = 0; i < N / 1000; i++) {
                a2.insert(a2.size() >> 1, i);
            }
        }, "a2中间插入");

        newLine();
    }

    @Test
    public void test3() {
        var l = new DoublyLinkedList<String>();
        l.insert("Jmc");
        l.insert("Ruby");
        l.insert("Merry");
        l.insert("OK");
        print(l);

        l.reverse();
        print(l);
    }
}
