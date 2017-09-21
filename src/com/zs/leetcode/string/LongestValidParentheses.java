package com.zs.leetcode.string;

import java.util.Stack;

public class LongestValidParentheses {

	public static void main(String[] args) {
		longestValidParentheses("(())");
	}
	
	public static int longestValidParentheses(String s) {
	 if(s==null||s.length()==0) {
            return 0;  
        }  
        int maxLength = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                	maxLength = Math.max(maxLength, i - stack.peek());
                }
            }
        }
        return maxLength;
    }
	
	public int longestValidParentheses_two(String s) {
	       if(s==null||s.length()==0) {  
	            return 0;  
	        }  
	        int start     = -1;  
	        int maxLength = 0;  
	        Stack stack   = new Stack();  
	        for(int i=0;i<s.length();i++) {
	            if(s.charAt(i)=='(') {  
	                stack.push(i);  
	            } else {
	                if(!stack.empty()) {
	                    stack.pop();  
	                    if(stack.empty()==true) {  
	                        maxLength = Math.max(i - start , maxLength);  
	                    } else {  
	                        maxLength = Math.max(i - (int)stack.peek() , maxLength);  
	                    }  
	                } else {
	                    start = i;  
	                }  
	            }  
	        }  
	        return maxLength; 
	    }
	
	public int longestValidParentheses_three(String s) {
	       if(s==null||s.length()==0) {  
	            return 0;  
	        }  
	        int len =  s.length();
	        int left = 0,right = 0,maxLength = 0;  
	        for(int i=0;i<len;i++) {
	            if(s.charAt(i)=='(') {
	               left++;  
	            }else{
	               right++;
	            }
	            
	            if(left == right){
	            	maxLength = Math.max(maxLength, 2*left);
	            }else if(right > left){
	            	left = right = 0;
	            }
	        }  
	        left = right = 0;
	        for(int i=len-1;i>0;i--) {
	            if(s.charAt(i)==')') {
	            	 right++;
	            }else{
	            	 left++; 
	            }
	            if(left == right){
	            	maxLength = Math.max(maxLength, 2*left);
	            }else if(left > right){
	            	left = right = 0;
	            }
	        }  
	        return maxLength; 
	    }

}
