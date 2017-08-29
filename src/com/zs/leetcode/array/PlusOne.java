package com.zs.leetcode.array;

import java.util.Arrays;

public class PlusOne {

	public static void main(String[] args) {
		int[] a = {11,10,19};
		plusOne_two(a);
	}

	public static int[] plusOne(int[] digits) {
		int c = 1;
		for(int i=digits.length -1;i>=0;i--){
			int a =  digits[i]+c;
			digits[i] = a % 10;
			c = a / 10;
		}
		int[] arr = new int[digits.length+1];
		if(c == 1){
			arr[0] = 1;
			System.arraycopy(digits, 0, arr, 1, digits.length);
		}else{
			System.out.println(Arrays.toString(digits));
			return digits;
		}
		System.out.println(Arrays.toString(arr));
		return arr;
	}
	
	public static int[] plusOne_two(int[] digits) {
		int c = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
        	int a =  digits[i]+c;
            if (digits[i] < 9) {
                ++digits[i];
                System.out.println(Arrays.toString(digits));
                return digits;
            }
            digits[i] = a % 10;
            c = a / 10;
        }
        int[] res = new int[digits.length + 1];
        res[0] = 1;
        System.arraycopy(digits, 0, res, 1, digits.length);
        System.out.println(Arrays.toString(res));
        return res;
	}

}
