package com.zs.leetcode.stack;

import java.util.Stack;

public class LongestValidParentheses {

	public static void main(String[] args) {
		System.out.println(longestValidParentheses("()(())"));
	}
	
	  public static int longestValidParentheses(String s) {
	        Stack<Integer> st = new Stack<Integer>();
	        int max = 0;
	        int start = 0;
	        for(int i=0;i<s.length();i++){
	        	if(s.charAt(i)=='('){
	        		st.push(i);
	        	}else{
	        		if(!st.isEmpty()){
	        				st.pop();
	        				if(!st.isEmpty()){
	        					max = Math.max(max, i - st.peek());
	        				}else{
	        					max = Math.max(max, i-start+1);
	        				}
	        		}else{
	        			start = i+1;
	        		}
					}
	        	}
	        return max;
	  }

}
