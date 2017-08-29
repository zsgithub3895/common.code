package com.zs.leetcode.array;

import java.util.Arrays;

public class FirstMissingPositive {

	public static void main(String[] args) {
		int[] nums = { 1, 0 };
		System.out.println(firstMissingPositive_two(nums));
	}

	public int firstMissingPositive(int[] nums) {
        int res = 1;
        if(nums == null || nums.length == 0){
			 return res;
		 }        
		 Arrays.sort(nums);
		 int m = 1;
		 int dup = nums[0];
		 for(int i= 0;i<nums.length;i++){
			 if(nums[i] > 0 && nums[i] != dup){
				 nums[m] = nums[i];
				 dup = nums[i];
				 m++;
			 }
		 }
		 int[] arr;
		 if(nums[0]>0){
			 arr = new int[m];
			 System.arraycopy(nums, 0, arr, 0, m);
		 }else{
			 arr = new int[m-1];
			 System.arraycopy(nums, 1, arr, 0, m-1);
		 }
		 boolean flag = true;
		 for(int k=0;k<arr.length;k++){
			 if(arr[k] != k+1){
				 flag = false;
				 res = k+1;
                break;
			 }
		 }
		 
		 if(flag){
			 res = arr.length+1;
		 }
	     return res;
   }

	public static int firstMissingPositive_two(int[] nums) {
		int i = 0;
		int n = nums.length;
		for (; i < n;) {
			if (nums[i] <= 0 || nums[i] == i + 1 || nums[i] > n || nums[i] == nums[nums[i] - 1]){
				i++; // 无效交换或位置正确
			}else {
				int temp = nums[i];
				nums[i] = nums[nums[i] - 1];
				nums[temp - 1] = temp;
			} // 交换到正确的位置上

		}

		for (i = 0; i < n; i++){
			if (nums[i] != i + 1)
				break; // 寻找第一个丢失的正数
		}
		return i + 1;
	}
	
}
