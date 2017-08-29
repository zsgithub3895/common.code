package com.zs.leetcode.string;

import java.util.Arrays;

public class ThreeSumClosest {

	public static void main(String[] args) {
		int[] nums = {0,1,2};
		System.out.println(threeSumClosest(nums, 3));
	}

	public static int threeSumClosest(int[] nums, int target) {
		    int min = Integer.MAX_VALUE;
			Arrays.sort(nums);
	        int res= 0;
			for (int i = 0; i < nums.length - 2; i++) {
				int left = i + 1;
				int right = nums.length - 1;
				while (left < right) {
	                int sum = nums[i] +nums[left] + nums[right];
	                if(sum > target){
	                    if(sum - target < min){
	                        min = sum - target;
	                        res = sum;
	                    }
	                    right--;
	                }else if(sum < target){
	                    if(target - sum < min){
	                        min = target -sum;
	                        res = sum;
	                    }
	                    left++;
	                }else{
						return sum;
	                }
	        
				}
			}
			return res;
	}
}
