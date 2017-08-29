package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetsWithDup {

	public static void main(String[] args) {
		int[] nums = {1,2,3};
		subsetsWithDup_two(nums);
	}

	public List<List<Integer>> subsetsWithDup(int[] nums) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (nums == null || nums.length == 0) {
			return res;
		}
		Arrays.sort(nums);
		List<Integer> list = new ArrayList<Integer>();
		sec(res, list, 0, nums);
		return res;
	}

	public void sec(List<List<Integer>> res, List<Integer> list, int start, int[] nums) {
		res.add(new ArrayList<Integer>(list));
		for (int i = start; i < nums.length; i++) {
			if (i > start && nums[i] == nums[i - 1])
				continue;
			list.add(nums[i]);
			sec(res, list, i + 1, nums);
			list.remove(list.size() - 1);
		}
	}

	public static List<List<Integer>> subsetsWithDup_two(int[] nums) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		result.add(new ArrayList<Integer>());
		Arrays.sort(nums);
		for (int i = 0; i < nums.length; i++) {
			int curSize = result.size();
			for (int j = 0; j < curSize; j++) {
				List<Integer> cur = new ArrayList<Integer>(result.get(j));
				//cur.addAll(result.get(j));
				cur.add(nums[i]);
				if (!result.contains(cur))
					result.add(cur);
			}
		}
		System.out.println(Arrays.asList(result.toArray()));
		return result;

	}

}
