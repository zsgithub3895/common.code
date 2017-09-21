package com.zs.leetcode.array;

import java.util.Arrays;

public class NextPermutation {

	public static void main(String[] args) {
		int[] nums = {3,3,0,3};
		nextPermutation(nums);
	}
	
    public static void nextPermutation(int[] nums) {
    	int len = nums.length;
    	int i=0,j=0;
	        for(i=len-2;i>=0;i--){
	        	if(nums[i+1]>nums[i]){
	        		for(j=len-1;j>i;j--){
	        			if(nums[j]>nums[i]) break;
	        		}
	        		int temp = nums[i];
	        		nums[i] = nums[j];
	        		nums[j] = temp;
	        		Arrays.sort(nums, i+1, len);
	        		System.out.println(Arrays.toString(nums));
	        		return;
	        	}
	        }
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
    }

}
