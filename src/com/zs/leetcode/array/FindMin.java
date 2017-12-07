package com.zs.leetcode.array;

public class FindMin {

	public static void main(String[] args) {
		int[] nums = {1, 2, 4, 5, 6, 7,0 };
		System.out.println(findMin(nums));
	}
	
	 public static int findMin(int[] nums) {
	        if(null == nums || nums.length == 0){
	            return Integer.MIN_VALUE;
	        }
	        int min = nums[0];
	        for(int i=1;i<nums.length;i++){
	        	if(min > nums[i]){
	        		   min = nums[i];
	        	}	            		
	        }
	        return min;
	    }
	 
	 public int findMin2(int[] nums) {
	        if (nums == null || nums.length == 0) {
	            return Integer.MIN_VALUE;
	        }
	        int left = 0, right = nums.length - 1;
	        while (left < right) {
	            int mid = (right - left) / 2 + left;
	            if(nums[mid] > nums[right]) {
	                left = mid + 1;
	            } else {
	                right = mid;
	            }
	        }
	        return nums[left];
	    }
	 
	 public static int findMin3(int[] nums) {
	        if(null == nums || nums.length == 0){
	            return Integer.MIN_VALUE;
	        }
	        int min = nums[0];
        	for(int i=0;i < nums.length-1;i++){
	        	 if(nums[i]>nums[i+1]){
	        		return  min > nums[i+1]? nums[i+1]:min;
	        	 }		
	        }
	        return min;
	    }

}
