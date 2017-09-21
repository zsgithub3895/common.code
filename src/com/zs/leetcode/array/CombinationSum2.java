package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum2 {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
    	List<List<Integer>> res = new ArrayList<List<Integer>>();
        Arrays.sort(candidates);
        backTracking(new ArrayList(),0, target,candidates,res);  
        return res;
    }  
      
    public static void backTracking(List<Integer> cur, int start, int target,int[] cans,List<List<Integer>> res) {  
        if (target == 0) {
        	List<Integer> list = new ArrayList<Integer>(cur);//cur只是引用，内容会变化，不能直接用cur
        	res.add(list);
        } else {
            for (int i = start; i < cans.length && cans[i] <= target; i++) {
            	 if (i != start && cans[i] == cans[i - 1]) {
                     continue;//去重
                 }
            		  cur.add(cans[i]);
                      backTracking(cur, i+1, target - cans[i],cans,res);
                      cur.remove(new Integer(cans[i]));
            }  
        }  
    }  
    
	public static void main(String[] args) {
		int[] candidates={10, 1, 1, 7, 6, 1, 5};//1,1,1,5,6,7,10;
		System.out.println(Arrays.asList(combinationSum(candidates,8)));
	}

}
