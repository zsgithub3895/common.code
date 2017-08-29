package com.zs.leetcode.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ThreeSum {

	public static void main(String[] args) {
		int[] num = { 0,0,0 };
		System.out.println(threeSum_two(num));

	}

	public static List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> all = new ArrayList<List<Integer>>();
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				for (int k = j + 1; k < nums.length; k++) {
					if (nums[i] + nums[j] + nums[k] == 0) {
						List<Integer> list = new ArrayList<Integer>();
						list.add(nums[i]);
						list.add(nums[j]);
						list.add(nums[k]);
						Collections.sort(list);
						if (!all.contains(list)) {
							all.add(list);
						}
					}
				}
			}

		}
		return all;
	}

	public static List<List<Integer>> threeSum_two(int[] nums) {
		List<List<Integer>> all = new ArrayList<List<Integer>>();
		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 2; i++) {
			int target = -nums[i];
			int left = i + 1, right = nums.length - 1;
			if (i == 0 || (i > 0 && nums[i] != nums[i - 1])) {
				while (left < right) {
					int a = nums[left];
					int b = nums[right];
					if (a + b == target) {
						List<Integer> list = new ArrayList<Integer>();
						list.add(a);
						list.add(target);
						list.add(b);
						all.add(list);
						while (left < right && nums[left] == nums[left + 1]) {
							left++;
						}
						while (left < right && nums[right] == nums[right - 1]) {
							right--;
						}
						left++;
						right--;
					} else if (a + b > target) {
						right--;
					} else {
						left++;
					}
				}
			}
		}
		return all;
	}
}
