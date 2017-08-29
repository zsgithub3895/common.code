package com.zs.leetcode.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MajorityElement {

	public static void main(String[] args) {

	}

	/*
	 * 原理：如果majority元素存在（majority元素个数大于n/2,个数超过数组长度一半）， 那么无论它的各个元素位置是如何分布的，
	 * 其count经过抵消和增加后，最后一定是大于等于1的。 如果不能保证majority存在，需要检验
	 */
	public static int majorityElement(int[] nums) {
		int maj = nums[0], count = 1;
		for (int i = 1; i < nums.length; i++) {
			if (count == 0) {
				maj = nums[i];
				count++;
			} else if (maj == nums[i]) {
				count++;
			} else {
				count--;
			}
			if (count > nums.length / 2 + 1) {
				return maj;
			}
		}
		return maj;
	}

	public boolean containsDuplicate(int[] nums) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < nums.length; i++) {
			if (set.contains(nums[i])) {
				return true;
			} else {
				set.add(nums[i]);
			}
		}
		return false;
	}

	

}
