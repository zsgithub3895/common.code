package com.zs.leetcode.stack;

import java.util.Stack;

public class IsValid {

	public static void main(String[] args) {
		System.out.println(isValid("()"));
	}

	public static boolean isValid(String s) {
		if (null == s || "".equals(s)) {
			return true;
		}
		Stack stack = new Stack();
		for (int i = 0; i < s.length(); i++) {
			char b = s.charAt(i);
			if (b != ')' && b != ']' && b != '}') {
				stack.push(b);
			} else {
				if (stack.size() == 0) {
					return false;
				}
				char curr = (char) stack.peek();
				switch (b) {
				case ')':
					if (curr == '(') {
						stack.pop();
					} else {
						return false;
					}
					break;

				case ']':
					if (curr == '[') {
						stack.pop();
					} else {
						return false;
					}
					break;

				case '}':
					if (curr == '{') {
						stack.pop();
					} else {
						return false;
					}
					break;
				}
			}
		}

		if (stack.size() == 0) {
			return true;
		}
		return false;
	}
}
