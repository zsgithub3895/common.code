package com.zs.algorithm.robert.one;

import java.util.Scanner;
/**
 * 输入两个正整数m和n，求其最大公约数和最小公倍数。
      程序分析：利用辗除法: 
* 在循环中，只要除数不等于0，用较大数除以较小的数，将小的一个数作为下一轮循环的大数，取得的余数作为下一轮循环的较小的数，如此循环直到较小的数的值为0，返回
* 较大的数，此数即为最小公约数，最小公倍数为两数之积除以最小公倍数。
 * */
public class CommonDivisor {
	public static void main(String[] args) {
		   int a, b;
		   Scanner s1 = new Scanner(System.in);
		   Scanner s2 = new Scanner(System.in);
		   a = s1.nextInt();
		   b = s2.nextInt();
		   int m = division(a, b);//辗转相除法
		   int n = a * b / m;
		   System.out.println("最大公约数: " + m);
		   System.out.println("最小公倍数: " + n);
		}
	
	public static int division(int x, int y) {
		   int t;
		   if(x < y) {
		    t = x;
		    x = y;
		    y = t;
		   }
		 
		   while(y != 0) {
			if (x == y) {
				return 1;
			} else {
				int k = x % y;
				x = y;
				y = k;
			}
		   }
		   return x;
		}
}
