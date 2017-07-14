package com.zs.leetcode.linkedlist;

public class DeleteDuplicates {

	public static void main(String[] args) {
		int[] arr = {0,0,0,0,0};
		ListNode linklist = new ListNode(0).create(arr);
		long t = System.currentTimeMillis();
		ListNode no = deleteDuplicates_two(linklist);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
		long tt = System.currentTimeMillis();
		System.out.print(" 消耗时间： " + (tt - t));
	}
	 public static ListNode deleteDuplicates_two(ListNode head) {
	        ListNode dummyNode = new ListNode(0);
	        dummyNode.next = head;
	        ListNode curr = dummyNode;
	        if(head == null || head.next == null){
	        	return head;
	        }
	        curr = curr.next;
	        while(curr != null){
	        	ListNode c_next = curr;
	        	while(c_next.next != null){
	        		if(curr.val == c_next.next.val){
	        			c_next.next = c_next.next.next;
	        		}else{
	        			c_next = c_next.next;
	        		}
	        	}
	        	curr = curr.next;
	        }
	        return dummyNode.next;
	 }
	 
	 public static ListNode deleteDuplicates(ListNode head) {
		 ListNode dummyNode = new ListNode(0);
	        dummyNode.next = head;
	        ListNode curr = dummyNode;
	        if(head == null || head.next == null){
	        	return head;
	        }
	        ListNode p = head;
	        while(p != null && p.next != null){
	        	if(p.val == p.next.val){
	        		int val = p.val;
	        		while(p != null && p.val == val ){
	        			curr.next = p;
	        			p = p.next;
	        		}
	        	}else{
	        		curr = p;
	        		p = p.next;
	        	}
	        }
	        return dummyNode.next;
	    }
}
