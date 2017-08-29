package com.zs.leetcode.array;

import java.util.Arrays;

public class MaxProduct {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public int maximumProduct(int[] nums) {
        if(nums == null || nums.length<3){
            return 0;
        }
        int n = nums.length;
        Arrays.sort(nums);
        int m = Math.max(nums[0]*nums[1],nums[n-2]*nums[n-3]);
        return nums[n-1]*m;
    }

	public int maximumProduct_two(int[] nums) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        for (int n: nums) {
            if (n <= min1) {
                min2 = min1;
                min1 = n;
            } else if (n <= min2) {     // n lies between min1 and min2
                min2 = n;
            }
            if (n >= max1) {            // n is greater than max1, max2 and max3
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n >= max2) {     // n lies betweeen max1 and max2
                max3 = max2;
                max2 = n;
            } else if (n >= max3) {     // n lies betwen max2 and max3
                max3 = n;
            }
        }
        return Math.max(min1 * min2 * max1, max1 * max2 * max3);
}
}
