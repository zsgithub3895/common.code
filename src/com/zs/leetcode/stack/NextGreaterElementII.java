package com.zs.leetcode.stack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class NextGreaterElementII {

	public static void main(String[] args) {
		int[] nums = { 100, 1, 11, 1, 120, 111, 123, 1, -1, -100 };
		System.out.println(Arrays.toString(nextGreaterElement_two(nums)));
	}

	public static int[] nextGreaterElement(int[] nums) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		Stack<String> stack = new Stack<String>();
		int[] res = new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			while (!stack.isEmpty() && Integer.valueOf(stack.peek().split("\\|", -1)[1]) < nums[i]) {
				map.put(Integer.valueOf(stack.peek().split("\\|", -1)[0]), nums[i]);
				stack.pop();
			}
			stack.push(i + "|" + nums[i]);
		}

		while (!stack.isEmpty()) {
			String[] next = stack.pop().split("\\|", -1);
			for (int j = 0; j < nums.length; j++) {
				if (Integer.valueOf(next[1]) < nums[j]) {
					map.put(Integer.valueOf(next[0]), nums[j]);
					break;
				}
			}
		}

		for (int i = 0; i < nums.length; i++) {
			res[i] = map.get(i) != null ? map.get(i) : -1;
		}
		return res;
	}

	public static int[] nextGreaterElement_two(int[] nums) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		int n = nums.length;
		int[] res = new int[n];
		for (int i = 0; i < 2 * n; i++) {
			int num = nums[i % n];
			while (!stack.isEmpty() && nums[stack.peek()] < num) {
				map.put(stack.peek(), num);
				stack.pop();
			}
			if (i < n) {
				stack.push(i);
			}
		}
		for (int i = 0; i < nums.length; i++) {
			res[i] = map.get(i) != null ? map.get(i) : -1;
		}
		return res;
	}
}
