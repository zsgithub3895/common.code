package com.zs.quick.sort;

import java.util.Arrays;
/**
 * 堆排序(Heapsort)是指利用堆积树（堆）这种数据结构所设计的一种排序算法，
 * 它是选择排序的一种。可以利用数组的特点快速定位指定索引的元素。
 * 堆分为大根堆和小根堆，是完全二叉树.
 * 堆是一种重要的数据结构，为一棵完全二叉树, 底层如果用数组存储数据的话，
 * 假设某个元素为序号为i(Java数组从0开始,i为0到n-1),如果它有左子树，
 * 那么左子树的位置是2i+1，如果有右子树，右子树的位置是2i+2，如果有父节点，
 * 父节点的位置是(n-1)/2取整。分为最大堆和最小堆，最大堆的任意子树根节点不小于任意子结点，
 * 最小堆的根节点不大于任意子结点
 * 
 * 
 * 步骤：
    1.构建最大堆。
    2.选择顶，并与第0位置元素交换
    3.由于步骤2的的交换可能破环了最大堆的性质，第0不再是最大元素，需要调用maxHeap调整堆(沉降法)，如果需要重复步骤2
 *
 *若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，
 *第 h 层所有的结点都连续集中在最左边，这就是完全二叉树
    */
public class HeapSort {
    public static void main(String[] args) {
        //int[] a={49,38,65,97,76,13,27,49,78,34,12,64};
        int[] a={46,79,56,38};
       /* int arrayLength=a.length;  
        //循环建堆  
        for(int i=0;i<arrayLength-1;i++){  
            //建堆  
            buildMaxHeap(a,arrayLength-1-i);  
            //交换堆顶和最后一个元素  
            System.out.println("---="+Arrays.toString(a)); 
            swap(a,0,arrayLength-1-i);  
            System.out.println(Arrays.toString(a));  
            
        }  */
    	
    	heapSort2(a);
    	
    }
    //对data数组从0到lastIndex建大顶堆
    public static void buildMaxHeap(int[] data, int lastIndex){
         //从lastIndex处节点（最后一个节点）的父节点开始 
        for(int i=(lastIndex-1)/2;i>=0;i--){// (lastIndex-1)/2 表示父节点
            //k保存正在判断的节点 
            int k=i;
            //如果当前k节点的子节点存在  
            while(k*2+1<=lastIndex){
                //k节点的左子节点的索引 
                int biggerIndex=2*k+1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if(biggerIndex<lastIndex){  
                    //若果右子节点的值较大  
                    if(data[biggerIndex]<data[biggerIndex+1]){  
                        //biggerIndex总是记录较大子节点的索引  
                        biggerIndex++;
                    }  
                }  
                //如果k节点的值小于其较大的子节点的值  
                if(data[k]<data[biggerIndex]){
                    //交换他们  
                	System.out.println(data[k]+"++++"+data[biggerIndex]);
                    swap(data,k,biggerIndex);  
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值  
                    k=biggerIndex;  
                }else{  
                    break;  
                }  
            }
        }
    }
    //交换
    private static void swap(int[] data, int i, int j) {  
        int tmp=data[i];  
        data[i]=data[j];  
        data[j]=tmp;
    } 
    
    public static void heapSort2(int[] array) {  
        if (array == null || array.length <= 1) {  
            return;  
        }  

        buildMaxHeap2(array);  
        System.out.println("++"+Arrays.toString(array));
        for (int i = array.length - 1; i >= 1; i--) {  
        	swap(array, 0, i);
        	System.out.println(Arrays.toString(array));
            maxHeap(array, i, 0);
            System.out.println("-----"+Arrays.toString(array));
        }  
    }  

    private static void buildMaxHeap2(int[] array) {  
        if (array == null || array.length <= 1) {  
            return;  
        }  

        int half = array.length / 2;  
        for (int i = half; i >= 0; i--) {  
            maxHeap(array, array.length, i);  
        }  
    }  

    private static void maxHeap(int[] array, int heapSize, int index) {  
        int left = index * 2 + 1;  
        int right = index * 2 + 2;  

        int largest = index;  
        if (left < heapSize && array[left] > array[index]) {  
            largest = left;  
        }  

        if (right < heapSize && array[right] > array[largest]) {  
            largest = right;  
        }  

        if (index != largest) {  
        	swap(array, index, largest);  
            maxHeap(array, heapSize, largest);  
        }  
    }  
}
