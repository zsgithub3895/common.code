package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Subsets {

	public static void main(String[] args) {
		int[] nums = {1,2,2};
		subsets_bit(nums);
	}

	public static List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (nums == null || nums.length == 0) {
			return res;
		}

		List<Integer> list = new ArrayList<Integer>();
		sec(res,list,0,nums);
		return res;
	}

	public static void sec(List<List<Integer>> res, List<Integer> list, int start, int[] nums) {
		res.add(new ArrayList<Integer>(list));
		for(int i=start;i<nums.length;i++){
			list.add(nums[i]);
			sec(res,list,i+1,nums);
			list.remove(list.size()-1);
		}
	}
	
	public static List<List<Integer>> subsets_bit(int[] nums) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (nums == null || nums.length == 0) {
			return res;
		}
		int length=nums.length;
        Arrays.sort(nums);
        for(int i=0;i<1<<length;i++)//num << 1,相当于num乘以2
        {
        	List<Integer> tmp = new ArrayList<Integer>();
            //计算i中有那几位为1
           for(int j=0;j<length;j++)
            {
                //判断i中第j位是否为1
                int bit = (i>>j)&1;
                if(bit == 1)
                {
                    tmp.add(nums[j]);
                }
            }
            res.add(tmp);
        }
        System.out.println(Arrays.asList(res.toArray()));
		return res;
	}


}
