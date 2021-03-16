package com.test.algorithm.list.sort;

import org.junit.Test;

public class Merge {
    @Test
    public void test() {
        ListNode l = new ListNode(3, new ListNode(1, new ListNode(4, new ListNode(2))));
        ListNode n = sortList(l);
        System.out.println(n);
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode slow = head, fast = head, tail = head;
        while (fast != null && fast.next != null) {
            tail = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        tail.next = null;

        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);
        return merge(l1, l2);
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(), n = root;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                n = n.next = l1;
                l1 = l1.next;
            } else {
                n = n.next = l2;
                l2 = l2.next;
            }
        }
        if (l1 != null) {
            n.next = l1;
        }
        if (l2 != null) {
            n.next = l2;
        }
        return root.next;
    }
}
