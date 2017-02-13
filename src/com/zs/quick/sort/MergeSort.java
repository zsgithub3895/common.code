package com.zs.quick.sort;

import java.util.Arrays;

public class MergeSort {
	
	public static void main(String[] args){
		int a[]={49,38,65,97,76,13,27,49};
		sort(a,0,a.length-1);
		System.out.println("---"+Arrays.toString(a));
	}
	
	public static void sort(int[] data, int left, int right) {
		System.out.println("++++++"+Arrays.toString(data));
	    if(left<right){
	        //找出中间索引
	        int center=(left+right)/2;
	        //对左边数组进行递归
	        sort(data,left,center);
	        //对右边数组进行递归
	        sort(data,center+1,right);
	        //合并
	        merge(data,left,center,right);
	    }
	}
	
	public static void merge(int[] data, int left, int center, int right) {
	    int [] tmpArr=new int[data.length];
	    int mid = center+1;
	    //third记录中间数组的索引
	    int third = left;
	    int tmp = left;
	    while(left<=center&&mid<=right){
	        //从两个数组中取出最小的放入中间数组
	        if(data[left]<=data[mid]){
	            tmpArr[third++]=data[left++];
	        }else{
	            tmpArr[third++]=data[mid++];
	        }
	    }
	    //剩余部分依次放入中间数组
	    while(mid<=right){
	        tmpArr[third++]=data[mid++];
	    }
	    while(left<=center){
	        tmpArr[third++]=data[left++];
	    }
	    //将中间数组中的内容复制回原数组
	    while(tmp<=right){
	        data[tmp]=tmpArr[tmp++];
	    }
	    System.out.println(Arrays.toString(data));
	}

	}
