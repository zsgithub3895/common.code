package com.zs.leetcode.stack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class MyStack {
	/** 方法一 */
	/*
	 * private Queue q1 = new LinkedList(); public MyStack() {
	 * 
	 * } public void push(int x) { q1.offer(x); } public int pop() {
	 * Collections.reverse((List<?>) q1); int p = (int) q1.poll();
	 * Collections.reverse((List<?>) q1); return p; } public int top() {
	 * Collections.reverse((List<?>) q1); int p = (int) q1.peek();
	 * Collections.reverse((List<?>) q1); return p; } public boolean empty() {
	 * return q1.isEmpty(); }
	 */

	/** 方法二 */
	private Queue<Integer> q1 = new LinkedList<Integer>();
	private Queue<Integer> q2 = new LinkedList<Integer>();

	public MyStack() {

	}

	public void push(int x) {
		q1.offer(x);
	}

	public int pop() {
		while (q1.size() > 1) {
			q2.offer(q1.poll());
		}
		int curr = q1.poll();
		Queue temp = q1;
		q1 = q2;
		q2 = temp;
		return curr;
	}

	public int top() {
		while (q1.size() > 1) {
			q2.offer(q1.poll());
		}
		int curr = q1.peek();
		Queue temp = q1;
		q1 = q2;
		q2 = temp;
		return curr;
	}

	public boolean empty() {
		return q1.isEmpty();
	}

	public static void main(String[] args) {
		/*
		 * MyStack my = new MyStack(); my.push(1); my.push(0);
		 * System.out.println(my.top()); System.out.println(my.pop());
		 * System.out.println(my.empty());
		 */
	}

}
