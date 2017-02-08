package com.zs.quick.sort;

import java.util.Arrays;
/**
 * 该方法的基本思想是：
1．先从数列中取出一个数作为基准数。
2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
3．再对左右区间重复第二步，直到各区间只有一个数。
虽然快速排序称为分治法，但分治法这三个字显然无法很好的概括快速排序的全部步骤。因此对快速排序作了进一步的说明：挖坑填数+分治法;
先从后向前找，再从前向后找。

一趟快速排序的算法是：
1）设置两个变量i、j，排序开始的时候：i=0，j=N-1；
2）以第一个数组元素作为关键数据，赋值给key，即key=A[0]；
3）从j开始向前搜索，即由后开始向前搜索(j--)，找到第一个小于key的值A[j]，将A[j]和A[i]互换；
4）从i开始向后搜索，即由前开始向后搜索(i++)，找到第一个大于key的A[i]，将A[i]和A[j]互换；
5）重复第3、4步，直到i=j； (3,4步中，没找到符合条件的值，即3中A[j]不小于key,4中A[i]不大于key的时候改变j、i的值，
使得j=j-1，i=i+1，直至找到为止。找到符合条件的值，进行交换的时候i， j指针位置不变。
另外，i==j这一过程一定正好是i+或j-完成的时候，此时令循环结束）。
 */
public class QuickSort {
	public static void main(String[] args) {
		int[] a = new int[] { 6, 2, 7, 3, 8, 9 };
		int low = 0;
		int high = 5;
		//sort(a, low, high);
		quickSort(a, low, high);
	}

	
	public static void sort(int arr[], int low, int high) {
		int l = low;
		int h = high;
		int povit = arr[low];//arr[low]即arr[l]就是第一个坑

		while (l < h) {
			while (l < h && arr[h] >= povit) {// 从右向左找小于povit的数来填s[i] 
				h--;
			}

			if (l < h) {
				/*int temp = arr[h];
				arr[h] = arr[l];
				arr[l] = temp;*/
				arr[l] = arr[h];//将arr[h]填到arr[l]中，arr[h]就形成了一个新的坑
				l++;
			}

			while (l < h && arr[l] <= povit) {
				l++;
			}

			if (l < h) {
				/*int temp = arr[h];
				arr[h] = arr[l];
				arr[l] = temp;*/
				arr[h] = arr[l];//将arr[l]填到arr[h]中，arr[l]就形成了一个新的坑
				h--;
			}
		}
		//退出时，i等于j。将x填到这个坑中
		arr[l]=povit;
		System.out.println(Arrays.toString(arr));
		System.out.println("povit=" + povit);
		if (l > low)
			sort(arr, low, l - 1);
		if (h < high)
			sort(arr, l + 1, high);
	}

	/*//////////////方式三：减少交换次数，提高效率/////////////////////*/
	/*private <T extends Comparable<? super T>> void quickSort(T[] targetArr, int start, int end) {
		int i = start, j = end;
		T key = targetArr[start];

		while (i < j) {
			 按j--方向遍历目标数组，直到比key小的值为止 
			while (j > i && targetArr[j].compareTo(key) >= 0) {
				j--;
			}
			if (i < j) {
				 targetArr[i]已经保存在key中，可将后面的数填入 
				targetArr[i] = targetArr[j];
				i++;
			}
			
			
			 按i++方向遍历目标数组，直到比key大的值为止 
			while (i < j && targetArr[i].compareTo(key) <= 0){
				i++;
			}
			
			 * 此处一定要小于等于零，假设数组之内有一亿个1，0交替出现的话，而key的值又恰巧是1的话，
			 * 那么这个小于等于的作用就会使下面的if语句少执行一亿次。
			 
			if (i < j) {
				 targetArr[j]已保存在targetArr[i]中，可将前面的值填入 
				targetArr[j] = targetArr[i];
				j--;
			}
		}
		 此时i==j 
		targetArr[i] = key;

		 递归调用，把key前面的完成排序 
		this.quickSort(targetArr, start, i - 1);

		 递归调用，把key后面的完成排序 
		this.quickSort(targetArr, j + 1, end);

	}*/
	
	public static void quickSort(int[] targetArr, int start, int end) {
		int i = start, j = end;
		int key = targetArr[start];

		while (i < j) {
			/* 按j--方向遍历目标数组，直到比key小的值为止 */
			while (j > i && targetArr[j]>=key) {
				j--;
			}
			if (i < j) {
				/* targetArr[i]已经保存在key中，可将后面的数填入 */
				targetArr[i] = targetArr[j];
				i++;
			}
			
			
			/* 按i++方向遍历目标数组，直到比key大的值为止 */
			while (i < j && targetArr[i]<=key){
				i++;
			}
			/*
			 * 此处一定要小于等于零，假设数组之内有一亿个1，0交替出现的话，而key的值又恰巧是1的话，
			 * 那么这个小于等于的作用就会使下面的if语句少执行一亿次。
			 */
			if (i < j) {
				/* targetArr[j]已保存在targetArr[i]中，可将前面的值填入 */
				targetArr[j] = targetArr[i];
				j--;
			}
		}
		/* 此时i==j */
		targetArr[i] = key;
		System.out.println("++++"+key);
		System.out.println(Arrays.toString(targetArr));
		if (i > start)
			quickSort(targetArr, start, i - 1);
		/* 递归调用，把key前面的完成排序 */
		if (j < end)
		/* 递归调用，把key后面的完成排序 */
		quickSort(targetArr, j + 1, end);

	}
}