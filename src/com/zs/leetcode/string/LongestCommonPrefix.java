package com.zs.leetcode.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongestCommonPrefix {

	public static void main(String[] args) {
		String s = "aad";
		String s2 = "aa";
		String[] strs = { s, s2 };
		// System.out.println(longestCommonPrefix(strs));
		System.out.println(isPalindrome(-2147483648));
	}

	public static String longestCommonPrefix(String[] strs) {
		if (strs == null || strs.length == 0)
			return "";

		String pre = strs[0];
		for (int i = 1; i < strs.length; i++) {
			if (pre.length() == 0 || strs[i].length() == 0) {
				return "";
			}
			while (strs[i].indexOf(pre) != 0) {
				pre = pre.substring(0, pre.length() - 1);
			}
		}
		return pre;
	}

	public static String longestCommonPrefix_two(String[] strs) {
		if (strs == null || strs.length == 0)
			return "";
		for (int i = 0; i < strs[0].length(); i++) {
			char c = strs[0].charAt(i);
			for (int j = 1; j < strs.length; j++) {
				if (i == strs[j].length() || strs[j].charAt(i) != c)
					return strs[0].substring(0, i);
			}
		}
		return strs[0];
	}

	private static int minlen(String[] strs) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < strs.length; i++)
			min = Math.min(min, strs[i].length());
		return min;
	}

	public static int reverse(int x) {
		StringBuilder sb = new StringBuilder();
		if (x > 0) {
			if (x < Integer.MAX_VALUE) {
				String s = x + "";
				for (int i = 0; i < s.length(); i++) {
					sb.append(s.charAt(s.length() - 1 - i));
				}
				String n = sb.toString();
				if (Long.valueOf(n) > Integer.MAX_VALUE) {
					x = 0;
				} else {
					x = Integer.valueOf(n);
				}
				return x;
			}
		} else {
			if (x > Integer.MIN_VALUE) {
				String s = Math.abs(x) + "";
				for (int i = 0; i < s.length(); i++) {
					sb.append(s.charAt(s.length() - 1 - i));
				}
				String n = sb.toString();
				if (-Long.valueOf(n) < Integer.MIN_VALUE) {
					x = 0;
				} else {
					x = -Integer.valueOf(n);
				}
				return x;
			}
		}
		return 0;
	}

	public static int reverse_two(int x) {
		long result = 0;
		int temp = Math.abs(x);
		while (temp > 0) {
			result *= 10;
			result += temp % 10;
			if (result > Integer.MAX_VALUE) {
				return 0;
			}
			temp /= 10;
		}
		return (int) (x > 0 ? result : -result);
	}

	public static int myAtoi(String str) {
		long res = 0l;
		str = str.trim();
		String prefix = "";
		if (str.startsWith("+") || str.startsWith("-")) {
			prefix = str.substring(0, 1);
			str = str.substring(1);
		}
		Pattern letter = Pattern.compile("\\D");
		Matcher m_let = letter.matcher(str);
		while (m_let.find()) {
			str = str.substring(0, m_let.start());
			break;
		}

		if("".equals(str) || str.length() == 0){
			return 0;
		}
		Long val = 0l;
		if (str.length() > 10 && prefix.equals("-")) {
			return Integer.MIN_VALUE;
		} else if (str.length() > 10 && !prefix.equals("-")) {
			return Integer.MAX_VALUE;
		} else {
			if (prefix.equals("-")) {
				val = -Long.valueOf(str);
			} else {
				val = Long.valueOf(str);
			}
			if (val > Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			} else if (val < Integer.MIN_VALUE) {
				return Integer.MIN_VALUE;
			} else {
				res = val;
			}
		}
		return (int) res;
	}

	   public static boolean isPalindrome(int x) {
	        x = Math.abs(x);
	        String s = x+"";
	        int len = s.length();
	        boolean flag = true;
	        for(int i=0;i<len/2;i++){
	            if(s.charAt(i) != s.charAt(len - i-1)){
	                flag = false;
	            }
	        }
	        return flag;
	    }
}
