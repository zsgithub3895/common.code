package com.zs.leetcode.array;

public class GenerateMatrix {

	public static void main(String[] args) {
		generateMatrix(3);
	}

	public static int[][] generateMatrix(int n) {
		int[][] arrMatrix = new int[n][n];
		int left = 0, top = 0, right = n - 1, bottom = n - 1;
		int num = 1;
		while (left < right && top < bottom) {
			for (int j = left; j < right; j++) {
				arrMatrix[top][j] = num++;
			}
			for (int j = top; j < bottom; j++) {
				arrMatrix[j][right] = num++;
			}
			for (int j = right; j > left; j--) {
				arrMatrix[bottom][j] = num++;
			}
			for (int j = bottom; j > top; j--) {
				arrMatrix[j][left] = num++;
			}
			left++;
			right--;
			top++;
			bottom--;
		}
		if(n % 2 == 1){
			arrMatrix[n/2][n/2] = num++;
		}
		return arrMatrix;
	}
}
