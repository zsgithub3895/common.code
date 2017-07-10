package com.zs.leetcode.linkedlist;

public class MergeList {

	public static void main(String[] args) {
		int[] a = { 6};
		int[] b = { 4,2,1};
		ListNode l1 = new ListNode(0).create(a);
		ListNode l2 = new ListNode(0).create(b);
		long t = System.currentTimeMillis();
		ListNode no = mergeTwoLists(l1, l2);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
		long tt = System.currentTimeMillis();
		System.out.print(" 消耗时间： "+(tt-t));
	}

	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		ListNode dummyNode = new ListNode(0);
		if(l1 == null){
			return l2;
		}
		if(l2 == null){
			return l1;
		}
		if(l1.val < l2.val){
			dummyNode.next = l1;
		}else{
			dummyNode.next = l2;
		}
		ListNode curr = dummyNode;
		while(l1 != null && l2 != null){
			if(l1.val < l2.val){
				curr.next = new ListNode(l1.val);
				l1 = l1.next;
			}else{
				curr.next = new ListNode(l2.val);
				l2 = l2.next;
			}
			curr = curr.next;
		}
		
		if(l1 != null){
			curr.next = l1;
		}
		
		if(l2 != null){
			curr.next = l2;
		}
		return dummyNode.next;
    }
	
	
	public static ListNode mergeTwoLists_two(ListNode l1, ListNode l2){
		ListNode dummyNode;
		if(null == l1){
			return l2;
		}
		if(null == l2){
			return l1;
		}
		if(l1.val < l2.val){
			dummyNode = l1;
			l1 = l1.next;
		}else{
			dummyNode = l2;
			l2 = l2.next;
		}
		ListNode curr = dummyNode;
		while(l1 != null && l2 != null){
			if(l1.val < l2.val){
				curr.next = l1;
				l1 = l1.next;
			}else{
				curr.next = l2;
				l2 = l2.next;
			}
			curr = curr.next;
		}
		if (l1 != null) {
			curr.next = l1;
		}
	     if(l2!=null){
	    	 curr.next=l2;
	     }
	 	return dummyNode;
	}
}
