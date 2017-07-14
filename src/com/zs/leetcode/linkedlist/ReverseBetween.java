package com.zs.leetcode.linkedlist;

import java.util.Stack;

public class ReverseBetween {

	public static void main(String[] args) {
		int[] b = {5,4,3,2,1};
		ListNode l2 = new ListNode(0).create(b);
		long t = System.currentTimeMillis();
		ListNode no = reverseBetween(l2,3,4);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
		long tt = System.currentTimeMillis();
		System.out.print(" 消耗时间： "+(tt-t));

	}
	 public static ListNode reverseBetween(ListNode head, int m, int n) {
		 ListNode dummyNode = new ListNode(0);
		 dummyNode.next = head;
		 if(head == null || head.next == null || m == n){
			 return head;
		 }
		 
		 Stack<ListNode> s = new Stack<ListNode>();
		 ListNode curr =  dummyNode;
		 ListNode p = head;
		 ListNode q = head;
		 int count=1;
		 while(p != null){
			if(count>=m && count<=n){
				s.add(p);
			}else if(count > n){
				break;
			}
			p = p.next;
			count++;
		 }
		 
		 int total = 1;
		 while(q != null){
			 if(total == m){
				 while(!s.isEmpty()){
					 ListNode temp = s.pop();
					 curr.next = new ListNode(temp.val);
					 curr = curr.next;
					 q = q.next;
				 }
				 curr.next = q;
				 break;
			 }else{
				 curr.next = q;
				 curr = curr.next;
				 q = q.next;
			 }
			 total ++;
		 }
		 
	    return dummyNode.next;
	 }
}
