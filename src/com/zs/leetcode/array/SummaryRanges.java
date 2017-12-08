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
		 int begin = 0;
		 int curr = 0;
		 for(int i=1;i < nums.length-1;i++){
			 if(nums[curr]+1 == nums[i]){
				 curr++;
			 }else{
				 if(nums[begin] != nums[curr]){
					 list.add(nums[begin]+"->"+nums[curr]); 
				 }else{
					 list.add(nums[begin]+""); 
				 }
				 begin = curr+1;
			 }
		 }
		 return list;
	 }

}
