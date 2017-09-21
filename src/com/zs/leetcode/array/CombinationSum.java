package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
    	List<List<Integer>> res = new ArrayList<List<Integer>>();
        Arrays.sort(candidates);
        backTracking(new ArrayList(), 0, target,candidates,res);  
        return res;
    }  
      
    public static void backTracking(List<Integer> cur, int from, int target,int[] cans,List<List<Integer>> res) {  
        if (target == 0) {
        	List<Integer> list = new ArrayList<Integer>(cur);//cur只是引用，内容会变化，不能直接用cur
            res.add(list);  
        } else {  
            for (int i = from; i < cans.length && cans[i] <= target; i++) {  
                cur.add(cans[i]);  
                backTracking(cur, i, target - cans[i],cans,res);
                //(list,0,7)->(list-2,0,5)->(list-2-2,0,3)->(list-2-2-2,0,1)X
                //                          (list-2-2,1,3)->(list-2-2-3,0,0)OK
                //                          (list-2-2,2,3)X
                //            (list-2,1,5)->(list-2-3,1,2)X
                //            (list-2,2,5)X
                //(list,1,7)->(list-3,1,4)->(list-3-3,1,1)X
                //(list,1,7)->(list-3,2,4)X
                //(list,2,7)->(list-6,2,1)X
                //(list,3,7)->(list-6,3,0)OK
                cur.remove(new Integer(cans[i]));  
            }  
        }  
    }  
    
	public static void main(String[] args) {
		int[] candidates={2,3,6,7};
		System.out.println(Arrays.asList(combinationSum(candidates,7)));
	}

}
