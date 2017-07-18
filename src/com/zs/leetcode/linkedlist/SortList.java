package com.zs.leetcode.linkedlist;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class SortList {

	public static void main(String[] args) {
		//int[] b = {1,2,6,3,4,5,6};
		int[] b = {1,2};
		ListNode l2 = new ListNode(0).create(b);
		//ListNode no = removeElements(l2,6);
		ListNode no  = insertionSortList(l2);
		while (no != null) {
			System.out.print(no.val + "  ");
			no = no.next;
		}
	}

	public static ListNode sortList(ListNode head) {
		ListNode dummyHead = new ListNode(0);
		ListNode curr = dummyHead;
		if (head == null || head.next == null) {
			return head;
		}
		Queue<ListNode> queue = new PriorityQueue<ListNode>(11,new Comparator<ListNode>(){
			@Override
			public int compare(ListNode o1, ListNode o2){
				return o1.val-o2.val;
			}
		});
		
		ListNode pre = head;
		while(pre != null){
			queue.offer(pre);
			pre = pre.next;
		}
		
		while(!queue.isEmpty()){
			ListNode temp = queue.poll();
			if(!queue.iterator().hasNext()){
				temp.next = null;
			}
			curr.next = temp;
			curr = curr.next;
		}
		return dummyHead.next;
	}
	
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    	if (headA == null || headB == null) {
			return null;
		}
    	ListNode first = headA;
    	ListNode second = headB;
    	Set set = new HashSet<ListNode>();
    	while(first !=null){
    		set.add(first);
    		first = first.next;
    	}
    	
    	while(second !=null){
    		if(set.contains(second)){
    			return second;
    		}
    		second = second.next;
    	}
    	return null;
    }
    
    public ListNode getIntersectionNode_two(ListNode headA, ListNode headB) {
    	if (headA == null || headB == null) {
			return null;
		}
    	ListNode first = headA;
    	ListNode second = headB;
    	int len1 = 0;
    	int len2 = 0;
    	while(first !=null){
    		first = first.next;
    		len1++;
    	}
    	while(second !=null){
    		second = second.next;
    		len2++;
    	}
    	first = headA;
    	second = headB;
    	if(len1 > len2){
    		int x = len1 - len2;
    		while(x != 0){
    			first = first.next;
    			x--;
    		}
    	}else if(len2 > len1){
    		int y = len2 - len1;
    		while(y != 0){
    			second = second.next;
    			y--;
    		}
    	}
    	
    	while(first != second){
    		first = first.next;
    		second = second.next;
    	}
    	return first;
    }
    
public static ListNode removeElements(ListNode head, int val) {
	ListNode dummyNode = new ListNode(0);
	dummyNode.next = head;
	ListNode curr = dummyNode;
	if(head == null){
		return null;
	}
	ListNode pre = head;
	while(pre != null){
		if(pre.val == val){
			if(pre.next != null){
				pre = pre.next;
			}else{
				pre = null;
				curr.next = pre;
				curr = curr.next;
			}
		}else if(pre.val != val){
			curr.next = pre;
			curr = curr.next;
			pre = pre.next;
		}
	}
	return dummyNode.next;
    }

	public static ListNode insertionSortList(ListNode head) {
		if(head == null || head.next == null){
			return head;
		}
		ListNode dummyNode = new ListNode(head.val);
		ListNode poitor = head.next;
		while(poitor != null){
			ListNode newHead = dummyNode;
			ListNode next = poitor.next;
			if(poitor.val <= dummyNode.val){
				ListNode oldHead = dummyNode;
				dummyNode = poitor;
				dummyNode.next = oldHead;
			}else {
				while(newHead.next != null){
					if(poitor.val > newHead.val && poitor.val <= newHead.next.val){
						ListNode middleNode = newHead.next;
						newHead.next = poitor;
						poitor.next = middleNode;
					}
					newHead = newHead.next;
				}
				
				if(newHead.next == null && poitor.val > newHead.val){
					newHead.next = poitor;
					poitor.next = null;
				}
			}
			poitor = next;
		}
		return dummyNode;
	}
	
}
