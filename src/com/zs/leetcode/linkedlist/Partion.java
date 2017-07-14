package com.zs.leetcode.linkedlist;

public class Partion {

	public static void main(String[] args) {
		int[] b = {1,2};
		ListNode l2 = new ListNode(0).create(b);
		long t = System.currentTimeMillis();
		ListNode testNode = partition(l2,2);
		ListNode testNode_other = testNode.next;
		while (testNode != null) {
			int d1 = testNode.val;
            int d2 = testNode_other.val;
            System.out.print(testNode.val+"  ");
            testNode = testNode.next; //每次迭代时，指针1走一步，指针2走两步
            if(testNode_other.next != null && testNode_other.next.next != null){
            	testNode_other = testNode_other.next.next;
            	 if(d1 == d2){//当两个指针重逢时，说明存在环，否则不存在。
                 	System.out.println("有环");
                 	break;
                 }
            }
		}
		long tt = System.currentTimeMillis();
		System.out.print(" 消耗时间： "+(tt-t));
	}

	public static ListNode partition(ListNode head, int x) {
		ListNode lessNode = new ListNode(0);
		ListNode greaterNode = new ListNode(0);
		ListNode node = head;
		ListNode less = lessNode;
		ListNode greater = greaterNode;
		if (head == null || head.next == null) {
			return head;
		}
		while(node != null){
			ListNode temp = node.next;//解决有环
			if (node.val < x) {
				less.next = node;
				less = less.next;
				less.next = null;
			} else {
				greater.next = node;
				greater = greater.next;
				greater.next = null;
			}
			node = temp;
		}
		less.next = greaterNode.next;
		return lessNode.next;
	}
}
