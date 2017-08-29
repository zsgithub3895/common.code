package com.zs.leetcode.array;

/**Container With Most Water*/
public class MaxArea {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public int maxArea(int[] height) {
	       if (height.length < 2) return 0;  
	        int ans = 0;  
	        int left = 0;  
	        int right = height.length - 1;  
	        while (left < right) {
	            int v = (right - left) * Math.min(height[left], height[right]);  
	            if (v > ans){
	                ans = v;
	            }  
	            if (height[left] < height[right]){ 
	                   left++;
	            }else{
	              right--;  
	            }  
	        }  
	        return ans; 
	    }
}
