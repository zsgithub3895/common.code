package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {

	public static void main(String[] args) {
		int[] nums = {1,2,3};
		System.out.println(Arrays.asList(permute(nums).toArray()));
	}
	
	public static List<List<Integer>> permute(int[] nums) {
		 List<List<Integer>> result = new ArrayList<List<Integer>>(); 
		 if(nums == null || nums.length==0) {
			 result.add(new ArrayList<Integer>());
			 return result;
		 }
	        dfs(0,nums,result);  
	        return result;  
	    }

	private static void dfs(int start, int[] nums, List<List<Integer>> result) {
		if(start==nums.length) {
			List<Integer> temp = new ArrayList<Integer>();
			for(int j=0;j<nums.length;j++) {  
				temp.add(nums[j]); 
           }
			result.add(temp);
			return;
		}
		
		for(int i=start;i<nums.length;i++) {
			swap(nums,i,start);
			dfs(start+1,nums,result);
			swap(nums,i,start);
		}
		
	}
   
   private static void swap(int[] nums,int i,int j){
       int t = nums[i];
			nums[i] = nums[j];
			nums[j] = t;
   }
	 
}
