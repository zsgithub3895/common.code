package com.zs.leetcode.array;

import java.util.Arrays;

public class CanPlaceFlower {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] flowerbed = {1,0,0,0,1};
		
		System.out.println(canPlaceFlowers(flowerbed,2));
	}

	 public static boolean canPlaceFlowers(int[] flowerbed, int n) {
		 for(int i=0;i<flowerbed.length;i++){
			 int next = (i == flowerbed.length - 1 ? 0 : flowerbed[i + 1]);
             int pre = (i == 0 ? 0 : flowerbed[i - 1]);
             if (next + pre == 0 && flowerbed[i] == 0) {
                 flowerbed[i] = 1;
                 n--;
             }
        }
        return n<=0;
	    }
}
