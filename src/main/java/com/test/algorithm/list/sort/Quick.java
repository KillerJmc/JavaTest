package com.test.algorithm.list.sort;

import org.junit.Test;

public class Quick {
    @Test
    public void test() {
        ListNode l = new ListNode(3, new ListNode(1, new ListNode(4, new ListNode(2))));
        ListNode n = sortList(l);
        System.out.println(n);
    }

    public ListNode sortList(ListNode head) {
        sort(head, null);
        return head;
    }

    private void sort(ListNode head, ListNode tail) {
        if (head == tail) return;
        var partition = partition(head, tail);
        sort(head, partition);
        sort(partition.next, null);
    }

    private ListNode partition(ListNode head, ListNode tail) {
        if (head == null || head.next == null) return head;

        ListNode p1 = head, p2 = head;
        int k = head.val;

        for (; p2 != tail; p2 = p2.next) {
            if (p2.val < k) {
                p1 = p1.next;
                swap(p1, p2);
            }
        }
        swap(head, p1);
        return p1;
    }

    private void swap(ListNode p1, ListNode p2) {
        int tmp = p1.val;
        p1.val = p2.val;
        p2.val = tmp;
    }
}
