package com.zs.leetcode.linkedlist;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
/**二叉查找树（Binary Search Tree），（又：二叉搜索树，二叉排序树）它或者是一棵空树，
*或者是具有下列性质的二叉树： 若它的左子树不空，
*则左子树上所有结点的值均小于它的根结点的值； 
*若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值；
* 它的左、右子树也分别为二叉排序树。 */
public class SortedListToBST {

	public static void main(String[] args) {
		int[] b = { 8,5,3 };
		ListNode l2 = new ListNode(0).create(b);
		TreeNode tn = sortedListToBST(l2);
		preOrderRec(tn);
	}

	public static TreeNode sortedListToBST(ListNode head) {
		TreeNode t = null;
		if (head == null) {
			return t;
		}
		int len = 0;
		ListNode p = head;
		while (p != null) {
			len++;
			p = p.next;
		}
		return createBST(head, 0, len-1);
	}

	public static TreeNode createBST(ListNode head, int low, int high) {
		if (low > high) {
			return null;
		}
		ListNode curr = head;
		int middle = (low + high) / 2;
		for (int i = low; i < middle; i++) {
			curr = curr.next;
		}
		TreeNode tn = new TreeNode(curr.val);
		tn.left = createBST(head, low, middle - 1);
		tn.right = createBST(curr.next, middle + 1, high);
		return tn;
	}

	public static void inOrderfun(TreeNode root) {
		if (root == null)
			return;
		Stack<TreeNode> s = new Stack<TreeNode>();
		Queue<Integer> q = new LinkedList<Integer>();// 用来存放遍历的结果
		while (root != null || !s.isEmpty()) {
			while (root != null) {
				s.push(root);// 先访问再入栈
				root = root.left;
			}
			root = s.pop();
			q.add(root.val);
			root = root.right;// 如果是null，出栈并处理右子树
		}
		System.out.println("中序遍历：");
		for (Integer i : q)
			System.out.print(i + " ");
	}
	
	public static void preOrderRec(TreeNode root){
        if(root!=null){
            System.out.print(root.val+","); 
            preOrderRec(root.left);
            preOrderRec(root.right);
        }  
    } 
}
