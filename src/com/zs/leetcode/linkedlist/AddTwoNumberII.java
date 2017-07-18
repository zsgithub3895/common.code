package com.zs.leetcode.linkedlist;

import java.util.Stack;

public class AddTwoNumberII {

	public static void main(String[] args) {
		int[] a = { 3,4,2,7};
		int[] b = { 4,6,5};
		ListNode l1 = new ListNode(0).create(a);
		ListNode l2 = new ListNode(0).create(b);
		long t = System.currentTimeMillis();
		ListNode no = new AddTwoNumbers().addTwoNumbersQQ(l1, l2);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
	}
	
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode node = new ListNode(0);
		ListNode curr = node;
		int flag = 0;
		Stack<ListNode> s_f = new Stack<ListNode>();
		Stack<ListNode> s_s = new Stack<ListNode>();
		Stack<ListNode> s_t = new Stack<ListNode>();
		while(l1 != null){
			s_f.push(l1);
			l1 = l1.next;
		}
		while(l2 != null){
			s_s.push(l2);
			l2 = l2.next;
		}
		
		while (!s_f.isEmpty() || !s_s.isEmpty()) {
			ListNode temp = null,temp2 = null;
			if(!s_f.isEmpty())  temp = s_f.pop();
			if(!s_s.isEmpty())  temp2 = s_s.pop();
			int i = (temp != null) ? temp.val : 0;
			int j = (temp2 != null) ? temp2.val : 0;
			int n = i + j;
			if (flag > 0) {
				n += 1;
			}
			if (n / 10 == 1) {
				s_t.push(new ListNode(n % 10));
				flag = 1;
			} else {
				s_t.push(new ListNode(n));
				flag = 0;
			}
		}
		if (flag > 0) {
			s_t.push(new ListNode(flag));
		}
		while(!s_t.isEmpty()){
			curr.next = s_t.pop();
			curr = curr.next;
		}
		return node.next;
	}
}
