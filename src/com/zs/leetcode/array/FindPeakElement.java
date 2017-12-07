package com.zs.leetcode.array;

public class FindPeakElement {

	public static void main(String[] args) {
		int[] nums = {1, 2, 3};
		System.out.println(findPeakElement(nums));
	}
	
	 public static int findPeakElement(int[] nums) {
		 if(null==nums || nums.length==0){
			 return Integer.MIN_VALUE;
		 }
		 int peak = nums[0];
		 if(nums.length == 1){
			 return 0;
		 }
		 if(nums.length == 2){
			 return peak>nums[1]?0:1;
		 }
		 for(int i=1;i<nums.length;i++){
			  if(peak <= nums[i]){
				  peak = nums[i];
			  }
		 }
		 for(int i=0;i<nums.length-2;i++){
			  if(nums[i]<nums[i+1] && nums[i+1] > nums[i+2]){
				  return i+1;
			  }
		 }
		 int index = 0;
		 for(int i=1;i<nums.length-1;i++){
			  if(peak <= nums[i+1]){
				  peak = nums[i+1];
				  index = i+1;
			  }
		 }
		 for(int i=0;i<nums.length-2;i++){
			  if(nums[i]<=nums[i+1] && nums[i+1] >= nums[i+2]){
				  return i+1;
			  }
		 }
	     return index;
	 }
	 
	 public int findPeakElement2(int[] nums) {
	        if (nums == null || nums.length < 1) return -1;
	        if (nums.length == 1) return 0;
	        int start = 0, end = nums.length - 1;
	        if (nums[0] > nums[1]) return 0;
	        if (nums[end] > nums[end - 1]) return end;    
	        while (start + 1 < end) {
	            int mid = start + (end - start) / 2;
	            if (nums[mid] < nums[mid - 1]) {
	                end = mid;
	            } else {
	                start = mid;
	            }           
	        }
	        if (nums[start] > nums[start - 1] && nums[start] > nums[start + 1]) return start;
	        return end;
	    }
	 
	 public int findPeakElement3(int[] nums) {
	        int l = 0, r = nums.length - 1;
	        while (l < r) {
	            int mid = (l + r) / 2;
	            if (nums[mid] > nums[mid + 1])
	                r = mid;
	            else
	                l = mid + 1;
	        }
	        return l;
	    }
	 
	 public int findPeakElement4(int[] nums) {
	        return search(nums, 0, nums.length - 1);
	    }
	    public int search(int[] nums, int l, int r) {
	        if (l == r)
	            return l;
	        int mid = (l + r) / 2;
	        if (nums[mid] > nums[mid + 1])
	            return search(nums, l, mid);
	        return search(nums, mid + 1, r);
	    }

}
