package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationsIII {

	public static void main(String[] args) {
		int[] nums = {1,1,2,2};
		System.out.println(Arrays.asList(permute(nums).toArray()));
	}
	
	public static List<List<Integer>> permute(int[] nums) {
		 List<List<Integer>> result = new ArrayList<List<Integer>>(); 
		 if(nums == null || nums.length==0) {
			 result.add(new ArrayList<Integer>());
			 return result;
		 }
	        dfs(0,nums,result);  
	        return result;  
	    }

	private static void dfs(int start, int[] nums, List<List<Integer>> result) {
		if(start>=nums.length) {
			List<Integer> temp = new ArrayList<Integer>();
			for(int j=0;j<nums.length;j++) {  
				temp.add(nums[j]); 
          }
			result.add(temp);
			return;
		}
		
		for(int i=start;i<nums.length;i++) {
			// if ( i != start && nums[i] == nums[start]) continue;错误！！！
			if(containsDuplicate(nums,start,i)) {
				swap(nums,i,start);
				dfs(start+1,nums,result);
				swap(nums,i,start);
			}
		}
		
	}
	

    private static boolean containsDuplicate(int[] arr, int start, int end) {
        for (int i = start; i <= end-1; i++) {
            if (arr[i] == arr[end]) {
                return false;
            }
        }
        return true;
    }
  
  private static void swap(int[] nums,int i,int j){
      int t = nums[i];
			nums[i] = nums[j];
			nums[j] = t;
  }
	
	
	/*public static List<List<Integer>> permute(int[] nums) {
		 List<List<Integer>> result = new ArrayList<List<Integer>>(); 
		 if(nums == null || nums.length==0) {
			 result.add(new ArrayList<Integer>());
			 return result;
		 }
		 ArrayList<Integer> item = new ArrayList<Integer>();
		 boolean [] visited = new boolean[nums.length];
		 Arrays.sort(nums);
	        dfs(item,nums,result,visited);  
	        return result;
	    }

	private static void dfs(ArrayList<Integer> item, int[] nums, List<List<Integer>> result, boolean[] visited) {
		if(item.size()==nums.length) {
			result.add(new ArrayList<Integer>(item));
			return;
		}
		
		for(int i=0;i<nums.length;i++) {
			if(i > 0 && nums[i] == nums[i-1] && !visited[i-1]) continue;
			if(!visited[i]) {
			    item.add(nums[i]);
				visited[i] = true;
				dfs(item,nums,result,visited);
				item.remove(item.size()-1);
				visited[i] = false;
			}
		}
		
	}*/
	 
}
