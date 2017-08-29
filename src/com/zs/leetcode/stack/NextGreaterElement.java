package com.zs.leetcode.stack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class NextGreaterElement {

	public static void main(String[] args) {
		int[] findNums = {1,3,5,2,4};
		int[] nums = {6,5,4,3,2,1,7};
		System.out.println(Arrays.toString(nextGreaterElement(findNums,nums)));
	}

	public static int[] nextGreaterElement(int[] findNums, int[] nums) {
        for(int i=0;i<findNums.length;i++){
            int x = findNums[i];
            for(int j=0;j<nums.length;j++){
                if(x == nums[j]){
                    if(j == nums.length-1){
                         findNums[i] = -1;
                    }else{
                    	int k = j;
                        while(k<nums.length-1){
                            int temp = nums[k+1];
                            if(temp > x){
                                findNums[i] = temp;
                                break;
                            }else{
                                findNums[i] = -1;
                            }
                            k++;
                        }           
                    }
                }
            }
        }
        return findNums;
    }
	
	public static int[] nextGreaterElement_two(int[] findNums, int[] nums) {
       HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
       Stack<Integer> stack = new Stack<Integer>();
       int[] res = new int[findNums.length];
       for(int num: nums){
    	   while(!stack.isEmpty() && stack.peek() < num){
    		   map.put(stack.peek(), num);
    		   stack.pop();
    	   }
    	   stack.push(num);
       }
       
       for(int i=0;i<findNums.length;i++){
    	   res[i] = map.get(findNums[i]) != null ? map.get(findNums[i]):-1;
       }
        return res;
    }
}
