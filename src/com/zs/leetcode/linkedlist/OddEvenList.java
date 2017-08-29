package com.zs.leetcode.linkedlist;

public class OddEvenList {

	public static void main(String[] args) {
		int[] arr = {5,4,3,2,1};
		ListNode linklist = new ListNode(0).create(arr);
		long t = System.currentTimeMillis();
		ListNode no = oddEvenList_two(linklist);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
		long tt = System.currentTimeMillis();
		System.out.print(" 消耗时间： " + (tt - t));
	}

	public static ListNode oddEvenList(ListNode head) {
		ListNode odd_all = new ListNode(0);
		ListNode even_all = new ListNode(0);
		ListNode curr = head;
		ListNode odd = odd_all;
		ListNode even = even_all;
		int count = 1;
		while(curr != null){
			ListNode next = curr.next;
			if(count % 2 == 0){
				even.next = curr;
				even = even.next;
			}else{
				odd.next = curr;
				odd = odd.next;
			}
			curr = next;
			count ++;
		}
		if(even != null){
			even.next = null;
		}
		if(odd != null){
			odd.next = even_all.next;
		}
		return odd_all.next;
    }
	
	 public static ListNode oddEvenList_two(ListNode head) {
	        if (head == null) return null;
	        ListNode odd = head, even = head.next, evenHead = even;
	        while (even != null && even.next != null) {
	            odd.next = even.next;
	            odd = odd.next;
	            even.next = odd.next;
	            even = even.next;
	        }
	        odd.next = evenHead;
	        return head;
	    }
}
