package com.zs.leetcode.linkedlist;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergerKList {

	public static void main(String[] args) {
		int[] a = {};
		int[] b = { 2,1 };
		int[] c = { 8,6,5 };
		ListNode l1 = new ListNode(0).create(a);
		ListNode l2 = new ListNode(0).create(b);
		ListNode l3 = new ListNode(0).create(c);
		ListNode[] k_merge = { l1, l2,l3 };
		long t = System.currentTimeMillis();
		ListNode no = mergeKList_two(k_merge);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
		long tt = System.currentTimeMillis();
		System.out.print(" 消耗时间： " + (tt - t));
	}

	public static ListNode mergeKLists(ListNode[] lists) {
		if (lists == null) {
			return null;
		}
		if (lists.length > 0) {
		}
		ListNode curr = lists[0];
		for (int i = 1; i < lists.length; i++) {
			ListNode temp = lists[i];
			// curr = mergeTwoLists(curr,temp);
		}
		return curr;
	}

	public static ListNode mergeKList_two(ListNode[] lists) {
		ListNode dummyHead = new ListNode(0);
		ListNode curr = dummyHead;
		if (lists == null || lists.length == 0)
			return null;

		PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(11, new Comparator<ListNode>() {
			public int compare(ListNode l1, ListNode l2) {
				return l1.val - l2.val;
			}
		});

		for (ListNode list : lists) {
			if (list != null)
				queue.offer(list);
		}

		while (!queue.isEmpty()) {
			ListNode temp = queue.poll();
			curr.next = temp;
			curr = curr.next;
			if (temp.next != null)
				queue.offer(temp.next);
		}
		return dummyHead.next;
	}

}
