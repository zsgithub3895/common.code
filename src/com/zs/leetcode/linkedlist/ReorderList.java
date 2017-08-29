package com.zs.leetcode.linkedlist;

public class ReorderList {
	public static void main(String[] args) {
		int[] b = {4,3,2,1};
		ListNode l2 = new ListNode(0).create(b);
		long t = System.currentTimeMillis();
		ListNode no = reorderList(l2);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
		long tt = System.currentTimeMillis();
		System.out.print(" 消耗时间： "+(tt-t));

	}
	public static ListNode reorderList(ListNode head) {
            ListNode dummy = new ListNode(0);
            ListNode newHead = new ListNode(0);
            ListNode secon = head;
            ListNode pre = head;
            ListNode first = head;
            ListNode curr = dummy;
            ListNode res = newHead;
            int len = 0;
            while(pre != null){
            	len ++;
            	pre = pre.next;
            }
            
            int half = len/2;
            while(half > 0 && first != null){
            	ListNode next = first.next;
            	curr.next = first;
            	curr = curr.next;
            	first = next;
            	half--;
            }
            if(curr != null){
            	curr.next = null;
    		}
            secon = first;
            ListNode prev = null;
		    while (secon != null) {
		        ListNode nextTemp = secon.next;
		        secon.next = prev;
		        prev = secon;
		        secon = nextTemp;
		    }
		    
		    dummy = dummy.next;
		    while(dummy != null){
		    	res.next = dummy;
		    	dummy = dummy.next;
		    	if(prev != null){
		    		res.next.next = prev;
		    		prev = prev.next;
		    		res = res.next.next;
		    	}else{
		    		res = res.next;
		    	}
		    }
		    return newHead.next;
	}
}
