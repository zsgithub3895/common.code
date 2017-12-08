package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.List;

public class SummaryRanges {

	public static void main(String[] args) {
		//Input: [0,1,2,4,5,7]
				//Output: ["0->2","4->5","7"]
		int[] nums = {0,1,2,4,5,7};
		
		System.out.println(summaryRanges(nums));
	}
	
	 public static List<String> summaryRanges(int[] nums) {
		 List<String> list = new ArrayList<String>();
		 if(null == nums || nums.length==0){
			 return list;
		 }
		 int left = 0;
		 int right = 0;
		 boolean together = false;
		 for(int i=1;i <= nums.length;i++){
			 if(i < nums.length && nums[right]+1 == nums[i]){
				 right++;
				 together = true;
			 }else{
				 if(together){
					 list.add(nums[left]+"->"+nums[right]);
					 together = false;
				 }else{
					 list.add(nums[left]+""); 
				 }
				 right++;
				 left = right;
				
			 }
		 }
		 return list;
	 }

}
