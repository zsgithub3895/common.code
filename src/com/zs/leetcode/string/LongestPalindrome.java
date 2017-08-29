package com.zs.leetcode.string;

public class LongestPalindrome {

	public static void main(String[] args) {
		int[] s = {3,2,2,3};
		System.out.println(removeElement(s,3));
	}

	public static String longestPalindrome(String s) {
		int max = 0;
		String longestSub = null;
		if ("".equals(s) || s.length() <= 1) {
			return s;
		}
		for (int i = 0; i < s.length(); i++) {
			for (int j = i + 1; j < s.length(); j++) {
				int len = j - i;
				String curr = s.substring(i, j + 1);
				if (isPalindrome(curr)) {
					if (len > max) {
						max = len;
						longestSub = curr;
					}
				}
			}
		}
		if (null == longestSub) {
			longestSub = s.substring(0, 1);
		}
		return longestSub;
	}

	public static boolean isPalindrome(String s) {
		int len = s.length();
		for (int i = 0; i < len / 2; i++) {
			if (s.charAt(i) != s.charAt(len - i - 1)) {
				return false;
			}
		}
		return true;
	}

	public static String longestPalindrome_two(String s) {
		if (s == null || s.length() <= 1)
			return s;

		int len = s.length();
		int maxLen = 1;
		boolean[][] dp = new boolean[len][len];
		String longest = null;
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len - i; j++) {
				int m = j + i;
				if (s.charAt(j) == s.charAt(m) && (m - j <= 2 || dp[j + 1][m - 1])) {
					dp[j][m] = true;

					if (m - j + 1 > maxLen) {
						maxLen = m - j + 1;
						longest = s.substring(j, m + 1);
					}
				}
			}
		}

		if (null == longest) {
			longest = s.substring(0, 1);
		}

		return longest;
	}

	public static String longestPalindrome_three(String s) {
		if (s == null || s.length() <= 1)
			return s;
		int len = s.length();
		String longest = s.substring(0, 1);
		for (int i = 0; i < len; i++) {
			String tmp = helper(s, i, i);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}

			tmp = helper(s, i, i + 1);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}
		}
		return longest;
	}

	public static String helper(String s, int begin, int end) {
		while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
			begin--;
			end++;
		}
		String subS = s.substring(begin + 1, end);
		return subS;
	}
	
public static int removeDuplicates(int[] nums) {
	if(nums.length == 0) return 0;
    int dup = nums[0];
    int end = 1;
    for(int i = 1; i < nums.length; i++){
        if(nums[i]!=dup){
            nums[end] = nums[i];
            dup = nums[i];
            end++;
        }
    }
    return end;
    }

public static int removeElement(int[] nums, int val) {
    int m = 0;
    for(int i= 0;i<nums.length;i++){
        if(nums[i] != val){
            nums[m] = nums[i];
            m++;
        }
    }
    return m;
}
}
