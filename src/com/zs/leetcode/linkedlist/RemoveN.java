package com.zs.leetcode.linkedlist;

public class RemoveN {
	//已知链表长度
	public static ListNode reNthFromEnd(ListNode head, int n) {
		ListNode nodeHead = new ListNode(0);
		nodeHead.next = head;
		int count=0;
		if (head == null || n == 0) {
			return null;
		}
		ListNode curr = head;
		while(curr!=null){
			count++;
			curr = curr.next;
		}
		curr = nodeHead;
		for (int i = 0; i < count - n ; i++) {
			curr = curr.next;
		}
		curr.next = curr.next.next;
		return nodeHead.next;
	}

	//不需要知道链表长度
	public static ListNode reNthFromEndFor(ListNode head, int n) {
		ListNode dummy = new ListNode(0);
		if(head == null || n == 0){
			return null;
		}
		dummy.next = head;
		ListNode first = dummy;
		ListNode second = dummy;
		for (int i = 0; i <= n; i++) {
			first = first.next;
		}

		while (first != null) {
			first = first.next;
			second = second.next;
		}
		second.next = second.next.next;
		return dummy.next;
	}

	public static void main(String[] args) {
		int[] a = {5,4,3,2,1};
		ListNode l1 = new ListNode(0).create(a);
		ListNode no = reNthFromEndFor(l1,2);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
	}
}
