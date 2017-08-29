package com.zs.leetcode.linkedlist;

public class CopyRandomList {

	public static void main(String[] args) {
		RandomListNode rn = new RandomListNode(1);
		rn.next = new RandomListNode(2);
		rn.random = new RandomListNode(3);
		rn.next.next = new RandomListNode(3);
		rn.next.random= new RandomListNode(4);
		copyRandomList(rn);
	}

  public static RandomListNode copyRandomList(RandomListNode head) {
	  if (head == null) {
          return null;
      }
      RandomListNode node = head;
      while (node != null) {
          // 复制一个新的结点
          RandomListNode newNode = new RandomListNode(node.label);
          // 将结点串接到被复制的结点后，并且依然组成单链表  原始：1->2->3->null，复制后：1->1->2->2->3->3->null
          newNode.next = node.next;
          node.next = newNode;
          node = newNode.next;
      }
      
      node = head;
      while (node != null) {
          //随机指针有指向某个具体的结点
          if (node.random != null) {
              //串接node被复制结点的随机指针
        	  node.next.random = node.random.next;
          }
          // 指向下一个被复制的结点
          node = node.next.next;
      }
      
      //新链表头
      RandomListNode newHead = head.next;
      //当前处理的被复制的结点
      node = head;
      while (node != null){
    	  RandomListNode temp = node.next;
          // node.next指向下一个被复制的结点
          node.next = temp.next;
          //下一个被复制的结点不为null
          if (temp.next != null) {
              //指向下一个复制的结点
        	  temp.next = temp.next.next;
          }
          //node指向下一个要被处理的被复制结点
          node = node.next;
      }

      return newHead;
    }
}
