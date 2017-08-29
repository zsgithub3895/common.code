package com.zs.leetcode.array;

import java.util.Arrays;

public class ThirdMax {

	public static void main(String[] args) {
		int[] nums = {1,2,2,5,3,5};
		System.out.println(thirdMax(nums));
	}
	
	public static int thirdMax(int[] nums) {
		 if(nums == null || nums.length == 0){
				return 0;
			}
		    Arrays.sort(nums);
	        int dup = nums[0];
		    int end = 1;
		    for(int i = 1; i < nums.length; i++){
		        if(nums[i]!=dup){
		            nums[end] = nums[i];
		            dup = nums[i];
		            end++;
		        }
		    }
		   int[] arr = new int[end];
		   for(int i=0;i<end;i++){
			   arr[i] = nums[i];
		   }
			if(arr.length<3){
				if(arr.length == 1){
					return arr[0];
				}else{
					return Math.max(arr[0], arr[1]);
				}
			}
	        return arr[arr.length-3];
	}

}
