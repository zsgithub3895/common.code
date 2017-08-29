package com.zs.algorithm.binary.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import javax.swing.text.AbstractDocument.BranchElement;

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
	
/**层序遍历* 
offer，add区别：
如果想在一个满的队列中加入一个新项，多出的项就会被拒绝： 新的offer方法就可以起作用了。它不是对调用 add()方法抛出一个 unchecked 异常，而只是得到由 offer() 返回的 false。 

poll，remove区别：remove()和 poll()方法都是从队列中删除第一个元素。remove() 的行为与 Collection 接口的版本相似，
但是新的 poll()方法在用空集合调用时不是抛出异常，只是返回 null。因此新的方法更适合容易出现异常条件的情况。

peek，element区别：element() 和 peek()用于在队列的头部查询元素。
与 remove()方法类似，在队列为空时，element()抛出一个异常，而 peek()返回 null*/
	public static void levelIterator(BinaryTree root){
		if(root == null) return;
		Queue<BinaryTree> q = new LinkedList<BinaryTree>();
		Queue<Integer> res = new LinkedList<Integer>();//用来存放遍历的结果
		q.offer(root);
		while(!q.isEmpty()){
			BinaryTree bt = q.poll();
			res.offer(bt.value);
			if(bt.left != null){
				q.offer(bt.left);
			}
			if(bt.right != null){
				q.offer(bt.right);
			}
		}
		
		System.out.println("层序遍历：");
		for (Integer i : res)
			System.out.print(i + " ");
	}
	

	public static void main(String[] args) {
		int[] array = {12,8,18};
		BinaryTree root = new BinaryTree(array[0]);//创建二叉树
		for(int i=1;i<array.length;i++){
		   root.insert(root, array[i]);//向二叉树中插入数据
		  }
		preOrderfun(root);
		System.out.println();
		inOrderfun(root);
		System.out.println();
		postOrderfun(root);
		System.out.println();
		levelIterator(root);
	}
}
