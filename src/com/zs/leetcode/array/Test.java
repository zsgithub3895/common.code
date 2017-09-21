package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List<List<Integer>> res = new  ArrayList<List<Integer>>();
		List<Integer> cur = new  ArrayList<Integer>();
		List<Integer> l1 = new  ArrayList<Integer>(cur);
		backTracking(l1,res);
		cur=l1;
		List<Integer> l2 = new  ArrayList<Integer>(cur);
		backTracking(l2,res);
		cur=l2;
		List<Integer> l3 = new  ArrayList<Integer>(cur);
		backTracking(l3,res);
		System.out.println(Arrays.asList(res));
	}
	
	 public static void backTracking(List<Integer> list,List<List<Integer>> res){
		 list.add(1);
		 res.add(list);
	 }

}
