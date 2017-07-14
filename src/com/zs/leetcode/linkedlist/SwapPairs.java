package com.zs.leetcode.linkedlist;

import java.util.Stack;

public class SwapPairs {

	public static void main(String[] args) {
		int[] arr = { 5,4,3,2,1};
		ListNode linklist = new ListNode(0).create(arr);
		long t = System.currentTimeMillis();
		ListNode no = swapPairs_three(linklist);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
		long tt = System.currentTimeMillis();
		System.out.print(" 消耗时间： " + (tt - t));

	}

	/**栈*/
	public static ListNode swapPairs(ListNode head) {
		ListNode dunmmyNode = new ListNode(0);
		ListNode curr = dunmmyNode;
		Stack<ListNode> st = new Stack<ListNode>();
		if(head == null){
			return null;
		}
		while(head != null){
			st.add(head);
			if(head.next == null && st.size()==1){
				curr.next = head;
			}
			if(st.size()==2){
				while(!st.isEmpty()){
					ListNode te = st.pop();
					curr.next = new ListNode(te.val);
					curr = curr.next;
				}
			}
			head = head.next;
			
		}
         return dunmmyNode.next;
	}
	
	/**指针*/
	public static ListNode swapPairs_two(ListNode head) {
		if(head == null || head.next == null){
			return head;
		}
		ListNode dunmmyNode = new ListNode(0);
		dunmmyNode.next = head;
		ListNode p1 = head;
		ListNode p2 = head.next;
		ListNode curr = dunmmyNode;
		while(p1 != null && p2 != null){
			curr.next = p2;
			 
	        ListNode t = p2.next;
	        p2.next = p1;
	        curr = p1;
	        p1.next = t;
	 
	        p1 = p1.next;
	 
	        if(t!=null)
	            p2 = t.next;
		}
         return dunmmyNode.next;
	}
	
	/**指针*/
	public static ListNode swapPairs_three(ListNode head){
		if(head == null || head.next == null)   
	        return head;
	 
	    ListNode dummyNode = new ListNode(0);
	    dummyNode.next = head;
	    ListNode curr = dummyNode;
	 
	    while(curr.next != null && curr.next.next != null){
	        //use t1 to track first node
	        ListNode t1 = curr;
	        curr = curr.next;
	        t1.next = curr.next;
	 
	        //use t2 to track next node of the pair
	        ListNode t2 = curr.next.next;
	        curr.next.next = curr;
	        curr.next = t2;
	    }
	 
	    return dummyNode.next;
	}
}
