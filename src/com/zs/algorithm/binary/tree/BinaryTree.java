package com.zs.algorithm.binary.tree;

class BinaryTree {
	public int value;
	public BinaryTree left;
	public BinaryTree right;

	public BinaryTree(int value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}

	public void insert(BinaryTree root, int data) { // 向二叉树中插入子节点
		if (data > root.value){// 二叉树的左节点都比根节点小
			if (root.right == null) {
				root.right = new BinaryTree(data);
			} else {
				this.insert(root.right, data);
			}
		} else { // 二叉树的右节点都比根节点大
			if (root.left == null) {
				root.left = new BinaryTree(data);
			} else {
				this.insert(root.left, data);
			}
		}
	}
}
