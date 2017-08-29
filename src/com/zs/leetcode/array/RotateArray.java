package com.zs.leetcode.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RotateArray {

	public static void main(String[] args) {
		int[] a = {1,2,3,4};
		rotate_two(a,3);
	}

	public static void rotate(int[] nums, int k) {
		int n = nums.length;
		int count = 0;
		int[] arr = new int[n]; 
	    for (int i = 0; i < n; i++) {
		     arr[(i+k)%n] = nums[i];
		} 
	    
	    for (int i = 0; i < n; i++) {
	    	nums[i] = arr[i];
		} 
		System.out.println(Arrays.toString(nums));
	}
	
	 public static void rotate_two(int[] nums, int k) {//1,2,3,4  
	        int temp, previous;
	        for (int i = 0; i < k; i++) {
	            previous = nums[nums.length - 1];
	            for (int j = 0; j < nums.length; j++) {
	                temp = nums[j];
	                nums[j] = previous;
	                previous = temp;
	            }
	        }
	        System.out.println(Arrays.toString(nums));  
	    }
	 
	 public boolean containsNearbyDuplicate(int[] nums, int k) {
	        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		        for(int i=0;i<nums.length;i++){
		            if(map.containsKey(nums[i])){
		            	if(i-map.get(nums[i]) < k){
		            		return true;
		            	}
		            }else{
	                    map.put(nums[i],i);
		            }
		        }
		        return false;
	    }
}
