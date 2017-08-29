package com.zs.leetcode.array;

import java.util.ArrayList;

public class PascalTrangle {

	public static void main(String[] args) {
		getRow(3);
	}

	/** 节省空间 
	 * 这里我们只需要一行数据，就得考虑一下是不是能只用一行的空间来存储结果而不需要额外的来存储上一行呢？
	 * 这里确实是可以实现的。对于每一行我们知道如果从前往后扫，第i个元素的值等于上一行的res[i]+res[i+1]，
	 * 可以看到数据是往前看的，如果我们只用一行空间，那么需要的数据就会被覆盖掉。
	 * 所以这里采取的方法是从后往前扫，这样每次需要的数据就是res[i]+res[i-1]，
	 * 我们需要的数据不会被覆盖，因为需要的res[i]只在当前步用，下一步就不需要了。
	 * 这个技巧在动态规划省空间时也经常使用，
	 * 主要就是看我们需要的数据是原来的数据还是新的数据来决定我们遍历的方向
	 * */
	public static ArrayList<Integer> getRow(int rowIndex) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		if (rowIndex < 0)
			return res;
		res.add(1);
		for (int i = 1; i <= rowIndex; i++) {
			for (int j = res.size() - 2; j >= 0; j--) {
				res.set(j + 1, res.get(j) + res.get(j + 1));
			}
			res.add(1);
		}
		return res;
	}

}
