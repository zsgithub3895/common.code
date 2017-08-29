package com.zs.leetcode.linkedlist;

public class Palindrome {

	public static void main(String[] args) {
		int[] b = {2,1};
		ListNode l2 = new ListNode(0).create(b);
		System.out.println(isPalindrome(l2));
	}

public static boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null){
        	return true;
        }
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        ListNode first = head;
        ListNode sec = head;
        int len = 0;
        while(first != null){
        	first = first.next;
        	len++;
        }
        int half = len/2;
        while(half > 0 && sec != null){
        	curr.next = sec;
        	curr = curr.next;
        	sec = sec.next;
        	half--;
        }
        if(curr != null){
        	curr.next = null;
        }
        ListNode prev = null;
        while(sec != null){
        	ListNode tempNext = sec.next;
        	sec.next = prev;
        	prev = sec;
        	sec = tempNext;
        }
        
        dummy = dummy.next;
        while(dummy != null && prev != null){
        	if(dummy.val != prev.val){
        		return false;
        	}
        	dummy = dummy.next;
        	prev = prev.next;
        }
        return true;
    }
}
