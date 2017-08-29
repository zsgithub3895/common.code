package com.zs.leetcode.stack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

public class ZigzagLevelOrder {

	public static void main(String[] args) {
		int[] array = {10,9,20,8,21};
		TreeNode root = new TreeNode(array[0]);// 创建二叉树
		for (int i = 1; i < array.length; i++) {
			root.insert(root, array[i]);// 向二叉树中插入数据
		}
		System.out.println(zigzagLevelOrder(root));
	}

	public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> list = new LinkedList<List<Integer>>();
        if(root == null){
            return list;
        }
        Stack<TreeNode> s1 = new Stack<TreeNode>();
        Stack<TreeNode> s2 = new Stack<TreeNode>();
        s1.push(root);
        while(!s1.isEmpty() || !s2.isEmpty()){
        	List<Integer> l1 = new ArrayList<Integer>();
        	List<Integer> l2 = new ArrayList<Integer>();
        	while(!s1.isEmpty()){
        		TreeNode curr = s1.peek();
        		s1.pop();
        		l1.add(curr.val);
        		if(curr.left != null)  s2.push(curr.left);
        		if(curr.right != null) s2.push(curr.right);
        	}
        	if(l1 != null && l1.size() > 0){
        		list.add(l1);
        	}
        	while(!s2.isEmpty()){
        		TreeNode curr = s2.peek();
        		s2.pop();
        		l2.add(curr.val);
        		if(curr.right != null) s1.push(curr.right);
        		if(curr.left != null)  s1.push(curr.left);
        	}
        	if(l2 != null && l2.size() > 0){
        		list.add(l2);
        	}
        }
        return list;
    }
}
