package com.zs.leetcode.array;

import java.util.Arrays;

public class FindUnsortedSubarray {

	public static void main(String[] args) {
		int[] nums ={1,2,3,4};
		System.out.println(findUnsortedSubarray(nums));

	}

	public static int findUnsortedSubarray(int[] nums) {
		int[] temp = nums.clone();
        Arrays.sort(nums);
        int start=0,end = nums.length-1;
        while(start < nums.length && temp[start]==nums[start]){
            start++;
        }
        while(end > start && temp[end]==nums[end]){
            end--;
        }
        return end - start+1;
	}
	
	public int findUnsortedSubarray_two(int[] nums) {
        int[] snums = nums.clone();
        Arrays.sort(snums);
        int start = snums.length, end = 0;
        for (int i = 0; i < snums.length; i++) {
            if (snums[i] != nums[i]) {
                start = Math.min(start, i);
                end = Math.max(end, i);
            }
        }
        return (end - start >= 0 ? end - start + 1 : 0);
    }

}
