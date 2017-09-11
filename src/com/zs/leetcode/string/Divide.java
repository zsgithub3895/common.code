package com.zs.leetcode.string;

public class Divide {

	public static void main(String[] args) {
		System.out.println(divide(-2147483648,2));
	}
/**
 * 求两数相除，而且规定我们不能用乘法，除法和取余操作，
 * 那么我们还可以用另一神器位操作Bit Operation，
 * 思路是，如果被除数大于或等于除数，则进行如下循环，定义变量t等于除数，定义计数p，
 * 当t的两倍小于等于被除数时，进行如下循环，t扩大一倍，p扩大一倍，然后更新res和m.
 *
 * */
	
	public static int divide(int dividend, int divisor) {
		if (divisor == 0 || (dividend == Integer.MIN_VALUE && divisor == -1)) return Integer.MAX_VALUE;
        int  res = 0;
        long m = Math.abs((long)dividend);
		long n = Math.abs((long)divisor);
        int sign = ((dividend < 0) ^ (divisor < 0)) ? -1 : 1;
        if (n == 1) return (int) (sign == 1 ? m : -m);
		while (m >= n) {//??
			long t = n,p=1;
			while(m>=(t<<1)){
				t = t<<1;
				p = p<<1;
			}
			res+=p;
			m-=t;
		}
		return sign > 0 ? res:-res;
	}
}
