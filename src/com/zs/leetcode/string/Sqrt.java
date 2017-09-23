package com.zs.leetcode.string;

public class Sqrt {

	public static void main(String[] args) {
		System.out.println(mySqrt(2147395599));
		System.out.println(mySqrt_two(2147395599));
	}
	
	 public static int mySqrt(int x) {
		 if(x<0) return -1; 
		 if(x == 0) return 0;
	     int l = 1;
	     int r = x/2 +1;
	     while(l<=r) {
	    	 int m = (l+r)/2;
	    	 if(x/m>=m && x/(m+1)<(m+1)) {
	    		 return m;
	    	 }
	    	 if(x/m<m) {
	    		 r = m-1;
	    	 }else {
	    		 l = m+1;
	    	 }
	     }
	     return 0;
	    }
	 
	 public static int mySqrt_two(int x) {
	        if (x <= 1) return x;
	        int lo = 1, hi = x;
	        while(lo < hi) {
	            long mid = (long)((hi + lo) >> 1);
	            if (x/mid >= mid && x/(mid+1)<(mid+1)) return (int)mid;
	            if (x/mid < mid) {
	            	hi = (int)mid-1;
	            }else {
	            	lo = (int)mid + 1;
	            }
	        }
	        return lo;
	    }

}
