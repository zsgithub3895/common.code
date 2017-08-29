package com.zs.leetcode.stack;

import java.util.Stack;

public class MinStack {
	private Stack<Integer> stack = new Stack<Integer>();
	private Stack<Integer> minStack = new Stack<Integer>();
	public MinStack() {

	}
	public void push(int x) {
		if(minStack.isEmpty() || x <= minStack.peek()){
			minStack.push(x);
		}
		stack.push(x);
	}

	public void pop() {
		int temp = stack.pop();
		if(temp == minStack.peek()){
			minStack.pop();
		}
	}

	public int top() {
		return (int) stack.peek();
	}

	public int getMin() {
		return minStack.peek();
	}

	public static void main(String[] args) {
		 MinStack minStack = new MinStack();
		 minStack.push(0);
		 minStack.push(1);
		 minStack.push(0);
		 System.out.println(minStack.getMin());
		 System.out.println(minStack.top());
		 
	
	}
}
