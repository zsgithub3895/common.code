package com.zs.leetcode.stack;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PostorderTraversal {

	public static void main(String[] args) {
		int[] array = { 12, 8, 18 };
		TreeNode root = new TreeNode(array[0]);// 创建二叉树
		for (int i = 1; i < array.length; i++) {
			root.insert(root, array[i]);// 向二叉树中插入数据
		}
		System.out.println(root.val);
		System.out.println(root.left.val);
		System.out.println(root.right.val);
		root = null;
		System.out.println(postorderTraversal(root));
	}

	public static List<Integer> postorderTraversal(TreeNode root) {
		List<Integer> list = new LinkedList<Integer>();
		Stack<TreeNode> s1 = new Stack<TreeNode>();
		Stack<TreeNode> s2 = new Stack<TreeNode>();
		TreeNode curr;
		s1.push(root);
		while (!s1.isEmpty()) {
			curr = s1.peek();
			if (curr != null) {
				s1.pop();
				s2.push(curr);
				if (curr.left != null)
					s1.push(curr.left);

				if (curr.right != null)
					s1.push(curr.right);
			}
		}

		while (!s2.isEmpty()) {
			list.add(s2.pop().val);
		}
		return list;
	}
}
