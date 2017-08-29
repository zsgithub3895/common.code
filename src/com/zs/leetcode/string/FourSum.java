package com.zs.leetcode.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> all = new ArrayList<List<Integer>>();
		 if(nums == null || nums.length<4){
	            return all;
	        }
		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 3; i++) {
			if (i != 0 &&  nums[i] == nums[i - 1])
				continue;
			for (int j = i + 1; j < nums.length-2; j++) {
				 if (j != i+1 && nums[j] == nums[j - 1]) 
					 continue;
				 int left = j + 1, right = nums.length - 1;
					while (left < right) {
						if ( nums[i]+nums[j]+nums[left] + nums[right] == target) {
							List<Integer> list = new ArrayList<Integer>();
							list.add(nums[i]);
							list.add(nums[j]);
							list.add(nums[left]);
							list.add(nums[right]);
							all.add(list);
							while (left < right && nums[left] == nums[left + 1]) {
								left++;
							}
							while (left < right && nums[right] == nums[right - 1]) {
								right--;
							}
							left++;
							right--;
						} else if (nums[i]+nums[j]+nums[left] + nums[right] > target) {
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
