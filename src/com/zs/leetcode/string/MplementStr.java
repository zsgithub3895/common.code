package com.zs.leetcode.string;

public class MplementStr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int strStr(String haystack, String needle) {
		int p = 0;
		if (haystack.contains(needle)) {
			p = haystack.indexOf(needle);
		}
		return p;
	}
}