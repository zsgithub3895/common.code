package com.zs.leetcode.string;

public class myPow {

	public static void main(String[] args) {
		System.out.println(myPow(3,3));
	}
	
	 public static double myPow(double x, int n) {
		 if(n > 0) {
			 return 1/pow(x,-n);
		 }else {
			 return pow(x,n);
		 }
	    }

	private static double pow(double x, int n) {
		if(n == 0) {
			return 1;
		}
		double d = pow(x,n/2);
		if(n%2 == 0) {
			return d*d;
		}else {
			return d*d*x;
		}
	}
	
	public static double myPow_two(double x, int n) {
		if (n == 0) return 1;
	    if (n == 1) return x;
	    if (n == -1) return 1 / x;
	    return myPow(x * x, n / 2) * myPow(x, n % 2);
	}
	
	public static double myPow_three(double x, int n){
		  if(x == 1.0){  
	            return x;  
	        }  
	       double sum = 1;  
	       double temp = x;  
	       int count = Math.abs(n);
	        while(count != 0){
	            if(count % 2 == 1){
	                sum = sum * temp;  
	            }  
	            temp = temp * temp;  
	            count = count >>> 1;   
	        }  
	       return n<0 ? 1/sum : sum;  
	}
}
