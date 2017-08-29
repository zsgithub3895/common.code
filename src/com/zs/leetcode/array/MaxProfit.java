package com.zs.leetcode.array;

public class MaxProfit {

	public static void main(String[] args) {
		int[] prices = { 7, 1, 5, 3, 6, 4 };
		System.out.println(maxProfit(prices));
	}

	/** 动态规划求最大利润 */
	public static int maxProfit(int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int local = 0;
		int global = 0;
		for (int i = 0; i < prices.length - 1; i++) {
			local = Math.max(local + prices[i + 1] - prices[i], 0);
			// int a = Math.abs(local);
			global = Math.max(local, global);
		}
		return global;

	}

	public int maxProfit_two(int prices[]) {
		int minprice = Integer.MAX_VALUE;
		int maxprofit = 0;
		for (int i = 0; i < prices.length; i++) {
			if (prices[i] < minprice) {
				minprice = prices[i];
			} else if (prices[i] - minprice > maxprofit) {
				maxprofit = prices[i] - minprice;
			}
		}
		return maxprofit;
	}
}
