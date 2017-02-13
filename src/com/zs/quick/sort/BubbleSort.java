package com.zs.quick.sort;

import java.util.Arrays;
/**
 * 当数据越接近正序时，冒泡排序性能越好
 * 冒泡排序是一种交换排序
 */
public class BubbleSort {

	public static void main(String[] args) {
		int score[] = {67, 69, 75, 87, 89, 90, 99, 100};//8
		//bubbleSort(score);
		bubbleSort_2(score);
	}
	public static void bubbleSort(int[] score) {
        for (int i = 0; i < score.length -1; i++){//最多做n-1趟排序
            for(int j = 0 ;j < score.length - i - 1; j++){
            	//对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
                if(score[j] < score[j + 1]){//把小的值交换到后面
                    int temp = score[j];
                    score[j] = score[j + 1];
                    score[j + 1] = temp;
                }
            }            
            System.out.print("第" + (i + 1) + "次排序结果：");
            System.out.println(Arrays.toString(score));
        }
            System.out.println("最终排序结果：");
            System.out.println(Arrays.toString(score));
	}
	
	// 对 bubbleSort 的优化算法
	public static void bubbleSort_2(int[] list) {
		int temp = 0; // 用来交换的临时数
		boolean bChange = false; //交换标志
		// 要遍历的次数
		for (int i = 0; i < list.length - 1; i++) {
			bChange = false;
			// 从后向前依次的比较相邻两个数的大小，遍历一次后，把数组中第i小的数放在第i个位置上
			for (int j = list.length - 1; j > i; j--) {
				// 比较相邻的元素，如果前面的数大于后面的数，则交换
				if (list[j - 1] > list[j]) {
					temp = list[j - 1];
					list[j - 1] = list[j];
					list[j] = temp;
					bChange = true;
				}
				// 如果标志为false，说明本轮遍历没有交换，已经是有序数列，可以结束排序
				if (false == bChange)
					break;
			}
			System.out.format("第 %d 趟：\t", i+1);
			System.out.println(Arrays.toString(list));
		}
	}

}
