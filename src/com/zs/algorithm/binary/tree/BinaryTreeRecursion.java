package com.zs.algorithm.binary.tree;
/**
 * recursion 递归，递推
 * */
public class BinaryTreeRecursion {
	public static void main(String[] args) {
		int[] array = {12,76,35,22,16,48,90,46,9,40};
		BinaryTree root = new BinaryTree(array[0]);//创建二叉树
		for(int i=1;i<array.length;i++){
		   root.insert(root, array[i]);//向二叉树中插入数据
		  }
		System.out.println("先序遍历:");
		preOrderRec(root);
		System.out.println();
		System.out.println("中序遍历:");
		inOrderRec(root);
		System.out.println();
		System.out.println("后序遍历:");
		postOrderRec(root);
	}
	
	  /** 
     * @param root 树根节点 (根节点->左孩子->右孩子)
     * 递归先序遍历 
     */
    public static void preOrderRec(BinaryTree root){
        if(root!=null){
            System.out.print(root.value+","); 
            preOrderRec(root.left);
            preOrderRec(root.right);
        }  
    }  
    /** 
     * @param root 树根节点 (左孩子->根节点->右孩子)
     * 递归中序遍历 
     */  
    public static void inOrderRec(BinaryTree root){
        if(root!=null){
        	inOrderRec(root.left);
            System.out.print(root.value+",");
            inOrderRec(root.right);
        }  
    }  
    /** 
     * @param root 树根节点 (左孩子->右孩子->根节点)
     * 递归后序遍历 
     */  
    public static void postOrderRec(BinaryTree root){
        if(root!=null){
        	postOrderRec(root.left);
        	postOrderRec(root.right);
            System.out.print(root.value+",");
        }  
    }  
}
