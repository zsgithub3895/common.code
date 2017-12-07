package com.zs.leetcode.array;

public class FindSubArray {

	public static void main(String[] args) {
		int[] nums = {2,3,1};
		System.out.println(minSubArrayLen(7,nums));
	}
	
	public static int minSubArrayLen(int s, int[] nums) {
		if(null == nums || nums.length==0){
			return 0;
		}
		int min = Integer.MAX_VALUE;
		for(int i=0;i<nums.length;i++){
			int minLen = getMinLenth(i,s,nums);
			if(minLen != -1 && min > minLen){
				min = minLen;
			}
		}
		return min == Integer.MAX_VALUE? 0 : min;
    }

	private static int getMinLenth(int i, int s, int[] nums) {
		if(nums[i]>=s){
			return 1;
		}
		int sum = 0;
		int count = 1;
		while(sum < s){
			sum += nums[i];
			if(sum >= s){
				break;
			}
			if(i == nums.length-1){
				count = -1;
				break;
			}
			i++;
			count++;
		}
		return count;
	}
	
	public int minSubArrayLen2(int s, int[] nums) {
        int left = 0, right = 0, sum = 0, minLength = Integer.MAX_VALUE;
        while(right < nums.length) {
            sum += nums[right++];
            while(sum>=s) {
                minLength = Math.min(minLength, right-left);
                sum -= nums[left++];
            }
        }
        
        return minLength == Integer.MAX_VALUE? 0 : minLength;
    }

}
