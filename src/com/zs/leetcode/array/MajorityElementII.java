package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MajorityElementII {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	  public List<Integer> majorityElement(int[] nums) {
		    List<Integer> list = new ArrayList<Integer>();
	        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
	        int len = nums.length;
	        for(int i=0;i<len;i++){
	            if(map.containsKey(nums[i])){
	                int val = map.get(nums[i])+1;
	                map.put(nums[i],val);
	            }else{
	                map.put(nums[i],0);
	            }
	        }
	        for(Entry<Integer, Integer> en:map.entrySet()){
	        	if(en.getValue() > len/3){
	        		list.add(en.getKey());
	        	}
	        }
	        return list;
	    }

}
