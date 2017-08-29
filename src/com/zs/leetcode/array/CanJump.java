package com.zs.leetcode.array;

public class CanJump {

	public static void main(String[] args) {
		//int[] A ={2,3,1,1,4};
		int[] A = { 3, 2, 1, 0, 4 };
		System.out.println(canJump(A));
	}

	public static boolean canJump(int[] nums) {
		if (nums == null || nums.length == 0)
			return false;
		int reach = 0;//用distance记录当前i之前跳的最远距离。如果distance< i，即代表即使再怎么跳跃也不能到达i
		for (int i = 0; i <= reach && i < nums.length; i++) {
			reach = Math.max(nums[i] + i, reach);
		}
		System.out.println(reach);
		if (reach < nums.length - 1)
			return false;
		return true;
	}
}
