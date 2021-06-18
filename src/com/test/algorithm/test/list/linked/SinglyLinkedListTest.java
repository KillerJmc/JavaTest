package com.test.algorithm.test.list.linked;

import com.test.algorithm.list.linked.impl.DoublyLinkedList;
import com.test.algorithm.list.linked.impl.SinglyLinkedList;
import org.junit.Test;

import static com.test.algorithm.list.linked.impl.SinglyLinkedList.Node;

public class SinglyLinkedListTest {
    public void print(SinglyLinkedList<?> l) {
        System.out.print("[");
        l.forEach(o -> System.out.print(o + ", "));
        System.out.print("\b\b]\n");
    }

    @Test
    public void test1() {
        var l = new SinglyLinkedList<String>();
        l.insert("Jmc");
        l.insert("Ruby");
        l.insert("Merry");
        print(l);

        System.out.println(l.indexOf("Merry"));
        System.out.println(l.indexOf("Hello"));

        l.insert(1, "OKSir");
        l.insert(l.size(), "Haha");
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
        var l = new SinglyLinkedList<String>();
        l.insert("Jmc");
        l.insert("Ruby");
        l.insert("Merry");
        print(l);

        l.reverse();
        print(l);
    }

    @Test
    public void test3() {
        var l = new SinglyLinkedList<String>();
        l.insert("Jmc");
        l.insert("Ruby");
        l.insert("Merry");
        l.insert("OKSir");
        l.insert("Lucy");
        print(l);

        System.out.println(l.getMid());
    }

    public boolean isCircle(Node<String> first) {
        Node<String> fast = first, slow = fast;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return true;
        }
        return false;
    }

    private Node<String> getEntrance(Node<String> first) {
        Node<String> fast = first, slow = fast, temp = null;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                temp = first;
                break;
            }
        }

        if (temp != null) {
            while (slow != temp) {
                slow = slow.next;
                temp = temp.next;
            }
        }

        return temp == null ? new Node<>(null, null) : temp;
    }

    @Test
    public void test4() {
        Node<String> seven = new Node<>("gg", null),
                         sixth = new Node<>("ff", seven),
                         fifth = new Node<>("ee", sixth),
                        fourth = new Node<>("dd", fifth),
                        third = new Node<>("cc", fourth),
                        second = new Node<>("bb", third),
                        first = new Node<>("aa", second);
        System.out.println(isCircle(first) ? "有环" : "无环");
        System.out.println("环的入口结点元素是" + getEntrance(first).item);

        seven.next = third;

        System.out.println(isCircle(first) ? "有环" : "无环");
        System.out.println("环的入口结点元素是" + getEntrance(first).item);
    }

    @Test
    public void test5() {
        // 解决约瑟夫问题
        var a = new SinglyLinkedList<DoublyLinkedList.Node<Integer>>();

        for (int i = 1; i <= 41 ; i++) a.insert(new DoublyLinkedList.Node<>(i, null, null));
        for (int i = 0; i + 1 < 41; i++) a.get(i).next = a.get(i + 1);
        for (int i = 40; i - 1 >= 0; i--) a.get(i).pre = a.get(i - 1);
        a.get(0).pre = a.get(40);
        a.get(40).next = a.get(0);

        var n = a.get(0);
        int count = 1;

        while (a.size() != 2) {
            if (count % 3 == 0) {
                n.pre.next = n.next;
                n.next.pre = n.pre;
                a.remove(n);
            }
            count++;
            n = n.next;
        }

        System.out.println(a);
    }
}
