package com.zs.leetcode.linkedlist;

public class AddTwoNumbers {
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode node = new ListNode(0);
		ListNode curr = node;
		int flag = 0;
		while (l1 != null || l2 != null) {
			int i = (l1 != null) ? l1.val : 0;
			int j = (l2 != null) ? l2.val : 0;
			int n = i + j;
			if (flag > 0) {
				n += 1;
			}
			if (n / 10 == 1) {
				curr.next = new ListNode(n % 10);
				flag = 1;
			} else {
				curr.next = new ListNode(n);
				flag = 0;
			}
			curr = curr.next;
			if (l1 != null)
				l1 = l1.next;
			if (l2 != null)
				l2 = l2.next;
		}
		if (flag > 0) {
			curr.next = new ListNode(flag);
		}
		return node.next;
	}

	public ListNode addTwoNumbersQQ(ListNode l1, ListNode l2) {
		ListNode dummyHead = new ListNode(0);// dummy虚的
		ListNode p = l1, q = l2, curr = dummyHead;
		int carry = 0;
		while (p != null || q != null) {
			int x = (p != null) ? p.val : 0;
			int y = (q != null) ? q.val : 0;
			int sum = carry + x + y;
			carry = sum / 10;
			curr.next = new ListNode(sum % 10);
			curr = curr.next;
			if (p != null)
				p = p.next;
			if (q != null)
				q = q.next;
		}
		if (carry > 0) {
			curr.next = new ListNode(carry);
		}
		return dummyHead.next;
	}

	public static void main(String[] args) {
		// Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
		// Output: 7 -> 0 -> 8
		int[] a = { 4,5,1};
		int[] b = { 7,6};
		ListNode l1 = new ListNode(0).create(a);
		ListNode l2 = new ListNode(0).create(b);
		long t = System.currentTimeMillis();
		ListNode no = new AddTwoNumbers().addTwoNumbers(l1, l2);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
		long t2 = System.currentTimeMillis();
		System.out.println("++++++"+(t2-t));
		System.out.println();
		ListNode node = new AddTwoNumbers().addTwoNumbersQQ(l1, l2);
		while (node != null) {
			System.out.print(node.val + "  ");
			node = node.next;
		}
		long t3 = System.currentTimeMillis();
		System.out.println("++++++"+(t3-t2));
	}
}
