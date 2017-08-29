package com.zs.leetcode.array;

public class RemoveDuplicates {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
