package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CombinationSum3 {

	public static List<List<Integer>> combinationSum(int k, int target) {
		int[] candidates = {1,2,3,4,5,6,7,8,9};
    	List<List<Integer>> res = new ArrayList<List<Integer>>();
        backTracking(new ArrayList(),0,target,candidates,res,k);  
        return res;
    }  
      
    public static void backTracking(List<Integer> cur, int start, int target,int[] cans,List<List<Integer>> res,int k) {  
        if (target == 0) {
        	List<Integer> list = new ArrayList<Integer>(cur);//cur只是引用，内容会变化，不能直接用cur
        	if(list.size() == k){
        		res.add(list);
        	}
        } else {
            for (int i = start; i < cans.length && cans[i] <= target; i++) {
            	 if (i != start && cans[i] == cans[i - 1]) {
                     continue;//去重
                 }
            		  cur.add(cans[i]);
                      backTracking(cur, i+1, target - cans[i],cans,res,k);
                      cur.remove(new Integer(cans[i]));
            }  
        }  
    }  
    
    
    public List<List<Integer>> combinationSum3(int k, int n) {
        // 用于保存所有结果
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        // 用于保存中间结果
        List<Integer> list = new LinkedList<Integer>();
        // 条件满足就进行解题操作
        if (k > 0 && k <= 9) {
             solve(k, 1, n, 0, list, result);
        }

        // 返回结果
        return result;
    }

    /**
     * 求解方法
     *
     * @param k         每个解的元素个数
     * @param cur       当前处理第k个元素
     * @param remainder k - cur + 1个元素的和
     * @param prevVal   第cur-1个元素的取值
     * @param list      将解的元素的集合类
     * @param result    保存所有结果的容器
     */
    public void solve(int k, int cur, int remainder, int prevVal, List<Integer> list, List<List<Integer>> result) {
        // 处理最后一个元素
        if (cur == k) {
            // remainder为最后一个解元素的值，它必须大于前一个解元素的值，并且不能超出9
            if (remainder > prevVal && remainder <= 9) {
                // 添加元素值
                list.add(remainder);

                // 拷贝结果到新的队列中
                List<Integer> one = new LinkedList<>();
                for (Integer i : list) {
                    one.add(i);
                }

                // 保存结果
                result.add(one);
                // 删除最后一个元素，进行现场还原
                list.remove(list.size() - 1);
            }
        }
        // 不是最后一个元素
        else if (cur < k){
            for (int i = prevVal + 1; i <= 9 - (k - cur); i++) {
                // 添加元素
                list.add(i);
                // 递归求解
                solve(k, cur + 1, remainder - i, i, list, result);
                // 现场还原
                list.remove(list.size() - 1);

            }
        }
    }
    
	public static void main(String[] args) {
		System.out.println(Arrays.asList(combinationSum(3,9)));
	}

}
