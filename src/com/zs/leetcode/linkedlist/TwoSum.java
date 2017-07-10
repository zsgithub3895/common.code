package com.zs.leetcode.linkedlist;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

	public static void main(String[] args) {
		int[] nums = {2, 7, 11, 15 };//三种格式 int[] nums = new int[]{2, 7, 11, 15} ,int[] res = new int[2];
		int target = 17;
		System.out.println(Arrays.toString(twoSum(nums, target)));
	}

	public static int[] twoSum(int[] nums, int target) {
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for (int i = 0; i < nums.length; i++) {
			int res = target- nums[i];
			if (map.containsKey(res)) {
				return new int[]{map.get(res),i};
			}
			map.put(nums[i], i);
		}
		throw new IllegalArgumentException("No two sum solution!");
	}
}
