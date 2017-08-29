package com.zs.leetcode.array;

import java.util.Arrays;

public class MedianTwoArray {

	public static void main(String[] args) {
       int[] a = {1,3,5,7};//k=,3
       int[] b = {2,4,6,8};//4
       System.out.println(findMedianSortedArrays(a,b));
	}
	
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int[] arr = new int[len1+len2];
        System.arraycopy(nums1, 0, arr, 0, len1);
        System.arraycopy(nums2, 0, arr, len1, len2);
        Arrays.sort(arr);
        double median = 0;
        int len = arr.length;
        if(len % 2 == 0){
        	median = (arr[len/2 - 1]+arr[len/2])/2.0;
        }else{
        	median = arr[len/2];
        }
        return median;
    }

}
