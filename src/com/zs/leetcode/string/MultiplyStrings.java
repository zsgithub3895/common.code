package com.zs.leetcode.string;

import java.math.BigInteger;
import java.util.Arrays;

public class MultiplyStrings {

	public static void main(String[] args) {
		System.out.println(multiply_three("123","15"));
	}

   public String multiply(String num1, String num2) {
	   BigInteger a =new BigInteger(num1);
	   BigInteger b = new BigInteger(num2);
	   BigInteger x = a.multiply(b);
	   return x+"";
    }
   
   public static String multiply_two(String num1, String num2) {
	  if(num1 == null || num2==null || num1.length() ==0|| num2.length() == 0) {
		  return "";
	  }
	  if(num1.equals("0") || num2.equals("0")) {
		  return "0";
	  }
	  
	  String n1 =new StringBuilder(num1).reverse().toString();
	  String n2 =new StringBuilder(num2).reverse().toString();
	  int[] arr = new int[n1.length()+n2.length()];
	  for(int i=0;i<n1.length();i++) {
		  for(int j=0;j<n2.length();j++) {
			  arr[i+j] +=(n1.charAt(i)-'0')*(n2.charAt(j)-'0');
		  }
	  }
	  
	  StringBuffer sb = new StringBuffer();
	  for(int k=0;k<arr.length;k++) {
		  int digit = arr[k]%10;
		  int carry = arr[k]/10;
		  if(k+1 < arr.length) {
			  arr[k+1] += carry;
		  }
		  sb.insert(0, digit);
	  }
	  if(sb.charAt(0)=='0' && sb.length()>0) {
		  sb.deleteCharAt(0);
	  }
	   return sb.toString();
    }
   
   public static String multiply_three(String num1, String num2) {
		  if(num1 == null || num2==null || num1.length() ==0|| num2.length() == 0) {
			  return "";
		  }
		  if(num1.equals("0") || num2.equals("0")) {
			  return "0";
		  }
		  
		  int[] arr = new int[num1.length()+num2.length()];
		  for(int i=num1.length()-1;i>=0;i--) {
			  for(int j=num2.length()-1;j>=0;j--) {
				  arr[i+j+1] +=(num1.charAt(i)-'0')*(num2.charAt(j)-'0');
				  
			  }
		  }
		  System.out.println(Arrays.toString(arr));
		  StringBuffer sb = new StringBuffer();
		  int carry=0;
		  for(int k=arr.length-1;k>=0;k--) {
			  int digit = (arr[k]+carry)%10;
			  carry = (arr[k]+carry)/10;
			  arr[k] = digit;
			  sb.insert(0, digit);
		  }
		  if(sb.charAt(0)=='0' && sb.length()>0) {
			  sb.deleteCharAt(0);
		  }
		   return sb.toString();
	    }
	
}
