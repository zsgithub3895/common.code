package com.zs.leetcode.linkedlist;

import java.util.Stack;

public class ReverseKGroup {

	public static void main(String[] args) {
		int[] arr = {5,4,3,2,1};
		ListNode linklist = new ListNode(0).create(arr);
		long t = System.currentTimeMillis();
		//ListNode no = reverseKGroup(linklist,3);
		ListNode no = rotateRight(linklist,4);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
		long tt = System.currentTimeMillis();
		System.out.print(" 消耗时间： " + (tt - t));
	}

public static ListNode reverseKGroup(ListNode head, int k) {
	ListNode dunmmyNode = new ListNode(0);
	ListNode curr = dunmmyNode;
	ListNode pre = head;
	Stack<ListNode> st = new Stack<ListNode>();
	if(head == null || head.next == null){
		return head;
	}
	int count = 0;
	int link_length = 0;
	while(head != null){
		st.add(head);
		if(st.size()==k){
			while(!st.isEmpty()){
				ListNode te = st.pop();
				curr.next = new ListNode(te.val);
				curr = curr.next;
				count ++;
				pre = pre.next;
			}
		}
		head = head.next;
		link_length ++;
	}
	if(count < link_length){
		curr.next = pre;
	}
	
     return dunmmyNode.next;
    }


public static ListNode rotateRight(ListNode head, int k) {
	if(head == null || head.next == null){
		return head;
	}
	ListNode dunmmyNode = new ListNode(0);
	dunmmyNode.next = head;
	ListNode curr = dunmmyNode;
	ListNode first = head;
	ListNode sencond = head;
	ListNode f_list = head;
	int link_length = 0;
	while(f_list != null){
		link_length++;
		f_list = f_list.next;
	}
	
	if(k>link_length || k<link_length){
	  int move_length =	k%link_length;
	  for(int i=0;i< move_length;i++){
			sencond = sencond.next;
		}
		while(sencond != null){
			first = first.next;
			sencond = sencond.next;
		}
		curr.next = first;
		int count = link_length - move_length;
		for(int j=0;j<move_length;j++){
			curr = curr.next;
		}
		for(int j=0;j<count;j++){
			curr.next = new ListNode(head.val);
	        head = head.next;
	        curr = curr.next;
		}
	}
    return dunmmyNode.next;
}
}
