package com.zs.leetcode.array;

public class MaxSubArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int maxSubArray(int[] nums) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			int tem = nums[i];
			if (tem > max) {
				max = tem;
			}
			for (int j = i + 1; j < nums.length; j++) {
				tem += nums[j];
				if (tem > max) {
					max = tem;
				}
			}
		}
		return max;
	}

	public int maxSubArray_two(int[] nums) {//动态规划
		if (nums == null || nums.length == 0)
			return 0;
		int max = nums[0];
		int local = nums[0];
		for (int i = 1; i < nums.length; i++) {
			local = Math.max(nums[i], local + nums[i]);
			max = Math.max(local, max);
		}
		return max;
	}

}
