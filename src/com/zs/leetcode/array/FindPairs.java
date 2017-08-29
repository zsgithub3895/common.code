package com.zs.leetcode.array;

import java.util.HashSet;
import java.util.Set;

public class FindPairs {

	public static void main(String[] args) {
		int[] a = {3,1,4,1,5};
    	System.out.println(findPairs(a,2));
	}
	  public static int findPairs(int[] nums, int k) {
	    	 if(k<0){
	             return 0;
	         }
	            Set set = new HashSet<Integer>();
	         for(int i=0;i<nums.length;i++){
	               for(int j=i+1;j<nums.length;j++){
	                 if(nums[j]-nums[i]==k){
	                 	set.add(nums[j]);
	                 }else if(nums[i] - nums[j] == k){
	                 	set.add(nums[i]);
	                 }
	         }
	         }
	         return set.size();
	    }
}
