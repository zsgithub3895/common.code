package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	 public List<Integer> spiralOrder(int[][] matrix) {
		 List<Integer> res = new ArrayList<Integer>();
		 if(matrix == null || matrix.length == 0){
			 return res;
		 }
		 int m = matrix.length;
		 int n = matrix[0].length;
		 int cycle = (Math.min(m, n)+1)/2;
		 for(int i=0;i<cycle;i++){
			 int lastRow = m - i -1;// 计算相对应的该圈最后一行
			 int lastCol = n - i -1;// 计算相对应的该圈最后一列
			 if(i == lastRow){ //如果该圈第一行就是最后一行，说明只剩下一行
				 for(int j=i;j<=lastCol;j++){
					 res.add(matrix[i][j]);
				 }
			 }else if(i==lastCol){//如果该圈第一列就是最后一列，说明只剩下一列
				 for(int j=i;j<=lastRow;j++){
					 res.add(matrix[j][i]);
				 }
			 }else{
				 for(int j=i;j < lastCol;j++){ // 第一行
					 res.add(matrix[i][j]);
				 }
				 for(int j=i;j < lastRow;j++){ // 最后一列
					 res.add(matrix[j][lastCol]);
				 }
				 for(int j=lastCol;j>i;j--){// 最后一行
					 res.add(matrix[lastRow][j]);
				 }
				 for(int j=lastRow;j>i;j--){// 第一列
					 res.add(matrix[j][i]);
				 }
			 }
		 }
		 return res;
	                 
	    }
}
