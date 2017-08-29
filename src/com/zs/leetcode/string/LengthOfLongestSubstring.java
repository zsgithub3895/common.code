package com.zs.leetcode.string;

import java.util.HashMap;
import java.util.HashSet;

public class LengthOfLongestSubstring {

	public static void main(String[] args) {
		String s = "abacsa";
		System.out.println("--" + lengthOfLongestSubstring_third(s));
	}

	public static int lengthOfLongestSubstring(String s) {
		if ("".equals(s) || s.length() == 0) {
			return 0;
		}
		HashSet hs = new HashSet();
		int max = 0;
		for (int i = 0; i < s.length(); i++) {
			hs.add(s.charAt(i));
			for (int j = i + 1; j < s.length(); j++) {
				if (hs.contains(s.charAt(j))) {
					max = Math.max(max, hs.size());
					hs.clear();
					break;
				} else {
					hs.add(s.charAt(j));
				}
			}
		}
		return Math.max(max, hs.size());
	}

	public static int lengthOfLongestSubstring_two(String s) {
		int ans = 0, left = 0;
		int[] arr = new int[128];
		for (int i = 0; i < s.length(); i++) {
			left = Math.max(arr[s.charAt(i)], left);
			arr[s.charAt(i)] = i + 1;
			ans = Math.max(ans, i - left + 1);
		}
		return ans;
	}

	public static int lengthOfLongestSubstring_third(String s) {
		if (s == null || s.length() == 0)
			return 0;
		// 新建一个map进行存储char
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		int leftBound = 0;
		int max = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			// 窗口左边可能为下一个char，或者不变
			leftBound = Math.max(leftBound, (map.containsKey(c)) ? map.get(c) + 1 : 0);
			max = Math.max(max, i - leftBound + 1);// 当前窗口长度
			map.put(c, i);
		}
		return max;
	}
	
}
