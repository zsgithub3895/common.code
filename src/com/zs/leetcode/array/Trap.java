package com.zs.leetcode.array;

public class Trap {

	public static void main(String[] args) {
           int[] arr = {0,1,0,2,1,0,1,3,2,1,2,1}; 
           System.out.println(trap_third(arr));
	}

	public static int trap(int[] height) {
		if(height == null || height.length ==0){
			return 0;
		}
		int max = height[0];
		for (int i = 1; i < height.length; i++) {
			//max = Math.max(max, height[i]);
			if (height[i] > max) {
				max = height[i];
			}
		}
		
		int index = 0;
		for (int i = 0; i < height.length; i++) {
			if (height[i] == max) {
				index = i;
			}
		}
		int[] a = new int[index+1];
		System.arraycopy(height, 0, a, 0, index+1);
		int[] b = new int[height.length-index]; 
		System.arraycopy(height, index, b, 0, height.length-index);
		
		int[] c = new int[b.length]; 
		for(int i=0;i<b.length;i++){
			b[i] = b[c.length-i-1];
		}
		int total=0;
		total = getTotal(a,total);
		total = getTotal(c,total);
		return total;
	}
	
	public static int getTotal(int[] param,int num){
		for (int i = 0; i < param.length; i++) {
			int curr = param[i];
			int k = i;
			for (int j = k + 1; j < param.length; j++) {
				if (curr < param[j]) {
					break;
				} else {
					int val = curr - param[j];
					num += val;
					i++;
				}
			}
		}
		return num;
	}
	
	
	public static int  trap_two(int[] height){//time:o(n) space:o(1)
	    int left = 0, right = height.length - 1;
	    int ans = 0;
	    int left_max = 0, right_max = 0;
	    while (left < right) {
	        if (height[left] < height[right]) {
	            if(height[left] >= left_max){
	            	left_max = height[left];
	            }else{
	            	ans += (left_max - height[left]);
	            }
	            ++left;
	        }
	        else {
	        	if(height[right] >= right_max){
	        		right_max = height[right];
	        	}else{
	        		ans += (right_max - height[right]);
	        	}
	            --right;
	        }
	    }
	    return ans;
	}
	
	public static int trap_third(int[] height){//time:o(n*n) space:o(1)
	    int ans = 0;
	    int size = height.length;
	    for (int i = 1; i < size - 1; i++) {
	        int max_left = 0, max_right = 0;
	        for (int j = i; j >= 0; j--) { //Search the left part for max bar size
	            max_left = Math.max(max_left, height[j]);
	        }
	        for (int j = i; j < size; j++) { //Search the right part for max bar size
	            max_right = Math.max(max_right, height[j]);
	        }
	        ans += Math.min(max_left, max_right) - height[i];
	    }
	    return ans;
	}
}
