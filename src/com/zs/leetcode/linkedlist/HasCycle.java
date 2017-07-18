package com.zs.leetcode.linkedlist;

public class HasCycle {

	public static void main(String[] args) {
		int[] b={2,1};
		ListNode l2 = new ListNode(0).create(b);
		ListNode no = detectCycle(l2);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
	}

	 public boolean hasCycle(ListNode head) {
	        if(head == null || head.next == null){
	        	return false;
	        }
	        ListNode pre = head;
	        ListNode second = head.next;
	        while(pre != null){
	        	int d1 = pre.val;
	        	int d2 = second.val;
	        	pre = pre.next;
	        	if(second.next != null && second.next.next != null){
	        		second = second.next.next;
	            	 if(d1 == d2){
	            		 return true;
	                 }
	            }
	        }
	        return false;
	  }
	 
	   public static ListNode detectCycle(ListNode head) {
		   if(head == null || head.next == null){
			   return null;
		   }
		   ListNode slow = head;
		   ListNode fast = head;
		   while(fast != null && fast.next != null){
	        	slow = slow.next;
	        	fast = fast.next.next;
	            if(slow == fast) break;
	        }
		   if (fast == null || fast.next == null) {
	            return null;
	        }
		   slow = head;
	        while (slow != fast){
	        	slow = slow.next;
	        	fast = fast.next;
	        }
		   return slow;
	    }
}
