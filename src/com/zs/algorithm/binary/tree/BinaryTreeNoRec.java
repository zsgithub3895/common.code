package com.zs.algorithm.binary.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class BinaryTreeNoRec {
	/**
	 * 12
	 *8  18
	*5 9
	 * */
	/**非递归实现先序遍历
	 * 将左子树点不断的压入栈，直到null，然后处理栈顶节点的右子树 
	 * */
	public static void preOrderfun(BinaryTree root) {
		if(root==null) return;
        Stack<BinaryTree> s=new Stack<BinaryTree>();
        Queue<Integer> q = new LinkedList<Integer>();// 用来存放遍历的结果
       //当前节点非空或者Stack非空时执行
        while(root!=null||!s.isEmpty()){
            while(root!=null){
                q.add(root.value);
                s.push(root);//先访问再入栈  
                root=root.left;
            }
            root=s.pop();
            root=root.right;//如果是null，出栈并处理右子树  
        }
        System.out.println("先序遍历：");
		for (Integer i : q)
			System.out.print(i + " ");
	}
	
	/**非递归实现中序遍历
	 * 思想和上面的相同，只是访问的时间是在左子树都处理完直到null的时候出栈并访问
	 * */
	public static void inOrderfun(BinaryTree root) {
		if(root==null) return;
        Stack<BinaryTree> s=new Stack<BinaryTree>();
        Queue<Integer> q = new LinkedList<Integer>();// 用来存放遍历的结果
        while(root!=null||!s.isEmpty()){
            while(root!=null){
                s.push(root);//先访问再入栈  
                root=root.left;
            }
            root=s.pop();
            q.add(root.value);
            root=root.right;//如果是null，出栈并处理右子树  
        }
        System.out.println("中序遍历：");
		for (Integer i : q)
			System.out.print(i + " ");
	}
	
	/**非递归实现后序遍历
	 * 后序遍历不同于先序和中序，它是要先处理完左右子树，然后再处理根(回溯)，
	 * 所以需要一个记录哪些节点已经被访问的结构(可以在树结构里面加一个标记)，这里可以用map实现
	 * */
	public static void postOrderfun(BinaryTree root) {
		if(root==null) return;
		Queue<Integer> q = new LinkedList<Integer>();//用来存放遍历的结果
		Stack<BinaryTree> s = new Stack<BinaryTree>();
		Map<BinaryTree,Boolean> map=new HashMap<BinaryTree,Boolean>();
		s.push(root);
		while (!s.isEmpty()) {
			BinaryTree temp = s.peek();
			if (temp.left != null && !map.containsKey(temp.left)) {
				temp = temp.left;
				while (temp != null) {
					if(map.containsKey(temp)){
						break;
					}else{
						s.push(temp);
					}
					temp = temp.left;
				}
				continue;
			}
			
			if (temp.right != null && !map.containsKey(temp.right)) {
				s.push(temp.right);
				continue;
			}
			
			BinaryTree t = s.pop();
			map.put(t, true);
			q.add(t.value);
		}
	    
		System.out.println("后序遍历：");
		for (Integer i : q)
			System.out.print(i + " ");
	}

	public static void main(String[] args) {
		int[] array = {12,8,5,9,18};
		BinaryTree root = new BinaryTree(array[0]);//创建二叉树
		for(int i=1;i<array.length;i++){
		   root.insert(root, array[i]);//向二叉树中插入数据
		  }
		preOrderfun(root);
		System.out.println();
		inOrderfun(root);
		System.out.println();
		postOrderfun(root);
	}
}
