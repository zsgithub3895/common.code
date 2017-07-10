package com.zs.leetcode.linkedlist;

public class ListNode {
	int val;
	ListNode next;
	
	//头结点的构造方法
	ListNode(int x) {
		val = x;
	}
	
	//非头结点的构造方法
    public ListNode(int obj, ListNode nextval) {
        this.val = obj;
        this.next = nextval;
    }

	public ListNode create(int[] num) {
		ListNode first = null;
		ListNode node = null;
		for (int i = 0; i < num.length; i++) {
			node = new ListNode(num[i]);
			node.next = first;
			first = node;
		}
		return node;
	}
	
}
