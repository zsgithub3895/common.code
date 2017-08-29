package com.zs.leetcode.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JumpII {

	public static void main(String[] args) {
		int[] arr = { 3, 3, 2, 2 };
		System.out.println(checkPossibility(arr));
	}

	public static int jump(int[] nums) {
		int res = 0, last = 0, cur = 0;
		for (int i = 0; i <= nums.length - 1; ++i) {
			if (i > last) {
				last = cur;
				++res;
				if (cur >= nums.length - 1)
					break;
			}
			cur = Math.max(cur, i + nums[i]);
		}
		return res;
	}

	public static boolean checkPossibility(int[] nums) {
		if (nums == null || nums.length <= 1) return true;
		 boolean modified = false;  
	     for (int i = 1; i < nums.length; i++) {  
	            if (nums[i] < nums[i - 1]) {
	                if(modified){
	                	 return false; 
	                } 
	                if(i - 2 < 0 || nums[i - 2] <= nums[i]){
	                	nums[i - 1] = nums[i];
	                }else{
	                	nums[i] = nums[i - 1];
	                }
	                modified = true;  
	            }  
	        }  
	        return true;  
	     
	}
}
