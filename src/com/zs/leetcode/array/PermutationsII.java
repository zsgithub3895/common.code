package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationsII {
	
	public static void main(String[] args) {
		int[] nums = {2,2,1,1};
		System.out.println(Arrays.asList(permuteUnique(nums).toArray()));
	}
	
	public static List<List<Integer>> permuteUnique(int[] nums) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		int len = nums.length;
		int i=0,j=0;
		if(!nextPermutation(nums)){
			if(nums.length == 1){
				List<Integer> list = new ArrayList<Integer>();
				for(int k=0;k<nums.length;k++){
					list.add(nums[k]);
				}
				res.add(list);
			}else if(nums.length == 2){
				List<Integer> list = new ArrayList<Integer>();
				for(int k=len-1;k>=0;k--){
					list.add(nums[k]);
				}
				res.add(list);
			}else{
				Arrays.sort(nums);
				List<Integer> list = new ArrayList<Integer>();
				for(int k=0;k<len;k++){
					list.add(nums[k]);
				}
				res.add(list);
			}
		}else{
			List<Integer> list = new ArrayList<Integer>();
			for(int k=0;k<nums.length;k++){
				list.add(nums[k]);
			}
			res.add(list);
		}
		while(nextPermutation(nums)){
			List<Integer> list_ = new ArrayList<Integer>();
			for(i=len-2;i>=0;i--){
	        	if(nums[i+1]>nums[i]){
	        		for(j=len-1;j>i;j--){
	        			if(nums[j]>nums[i]) break;
	        		}
	        		int temp = nums[i];
	        		nums[i] = nums[j];
	        		nums[j] = temp;
	        		Arrays.sort(nums, i+1, len);
                    for(int k=0;k<nums.length;k++){
				                 list_.add(nums[k]);
			               }
                    res.add(list_);
                    break;
	        	}
	        }
		}
		return res;
	}
	
	 public static boolean nextPermutation(int[] nums) {
	        for(int i=nums.length-2;i>=0;i--){
	        	if(nums[i+1]>nums[i]){
	        		return true;
	        	}
	        }
	        return false;
	    }
	}
