package com.zs.leetcode.linkedlist;

import java.util.Stack;

public class ReverseList {

	public static void main(String[] args) {
		int[] a = { 3,4,2,7};
		ListNode l1 = new ListNode(0).create(a);
		long t = System.currentTimeMillis();
		ListNode no = reverseList(l1);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
	}
	 public static ListNode reverseList(ListNode head) {
		 if(head == null || head.next == null){
	        	return head;
	        }
	        ListNode dummyNode = new ListNode(0);
	        ListNode curr = dummyNode;
	        Stack<ListNode> s = new Stack<ListNode>();
	        ListNode pre = head;
	        while(pre != null){
	        	s.push(pre);
	        	pre = pre.next;
	        }
	        while(!s.isEmpty()){
	        	curr.next = s.pop();
	        	curr = curr.next;
	        }
	        curr.next = null;
	        return dummyNode.next;
	    }
	 
	 public ListNode reverseList_two(ListNode head) {
		    ListNode prev = null;
		    ListNode curr = head;
		    while (curr != null) {
		        ListNode nextTemp = curr.next;
		        curr.next = prev;
		        prev = curr;
		        curr = nextTemp;
		    }
		    return prev;
		}
	 
	 public void deleteNode(ListNode node){
	        if(node.next != null){
	        	int temp = node.next.val;
	        	node.next.val  = node.val;
	        	node.val = temp;
	        	node.next = node.next.next;
	        }
	    }
}
