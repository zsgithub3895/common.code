package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindDupplicates {

	public static void main(String[] args) {
		int[] nums = {4,3,2,7,8,2,3,1};
		findDuplicates(nums);
	}
	 public static List<Integer> findDuplicates(int[] nums) {
	        List<Integer> res = new ArrayList<Integer>();
	        if(nums.length == 0) return res;
	        Arrays.sort(nums);
		    int dup = nums[0];
		    int end = 1;
		    for(int i = 1; i < nums.length; i++){
		        if(nums[i]!=dup){
		            nums[end] = nums[i];
		            dup = nums[i];
		            end++;
		        }else{
		        	System.out.println(nums[i]);
	                res.add(nums[i]);
	            }
		    }
		    return res;
	    }

}
