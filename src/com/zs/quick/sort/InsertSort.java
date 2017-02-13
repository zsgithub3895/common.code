package com.zs.quick.sort;

import java.util.Arrays;

/**
 * 每次将一个待排序的元素与已排序的元素进行逐一比较，直到找到合适的位置按大小插入.
 * 直接插入排序是一种稳定排序。
 * */
public class InsertSort {

	public static void main(String[] args) {
             int[] arr =new int[]{8,7,4,2};
             insertSort(arr);
             
	}
	public static void insertSort(int[] arr){
		 for(int i = 1;i < arr.length; i ++){
	         if(arr[i] < arr[i-1]){//注意[0,i-1]都是有序的。如果待插入元素比arr[i-1]还大则无需再与[i-1]前面的元素进行比较了，反之则进入if语句
	             int temp = arr[i];
	             int j = 0;
	             for(j = i-1; j >= 0 && arr[j] > temp; j --){                
	                     arr[j+1] = arr[j];//把比temp大或相等的元素全部往后移动一个位置            
	             }
	             arr[j+1] = temp;//把待排序的元素temp插入腾出位置的(j+1)
	         }  
	         System.out.println(Arrays.toString(arr));
	     }
		 //System.out.println(Arrays.toString(arr));
	}
}
