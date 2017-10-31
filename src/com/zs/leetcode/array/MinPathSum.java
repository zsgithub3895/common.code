package com.zs.leetcode.array;

public class MinPathSum {

	public static void main(String[] args) {
		int[][] grid = new int[][]{
			{1,3,1},
			{1,5,1},
			{4,2,1}
			};
		System.out.println(minPathSum2(grid));
	}
	
	 public static int minPathSum(int[][] grid) {
		   if(grid==null || grid.length==0 || grid[0].length==0) return 0;
	        int row = grid.length;
	        int col = grid[0].length;
	        int[][] res = new int[row][col];
	        res[0][0] = grid[0][0];
	        for(int i=1;i<row;i++){
	        	res[0][i] = res[0][i-1] + grid[0][i];
	        }
	        for(int i=1;i<col;i++){
	        	res[i][0] = res[i-1][0] + grid[i][0];
	        }
	        for(int i=1;i<row;i++){
	            for(int j=1;j<col;j++){
	            	res[i][j] = grid[i][j]+Math.min(res[i-1][j],res[i][j-1]);
	            }
	        }
	        return res[row-1][col-1];
	    }
	 
	 public static int minPathSum2(int[][] grid) {
		    if(grid==null || grid.length==0 || grid[0].length==0) return 0;
	        int[][] minSum = new int[grid.length][grid[0].length];
	        return helper(grid, 0, 0, minSum);
	        //[[7, 6, 3],
	        // [8, 7, 2], 
	        // [7, 3, 0]]
	    }
	    public static int helper(int[][] grid, int i, int j, int[][] minSum) {
	        if (i == grid.length - 1 && j == grid[0].length - 1) return grid[i][j];
	        if (minSum[i][j] != 0) return minSum[i][j];
	        int down = Integer.MAX_VALUE, right = Integer.MAX_VALUE;
	        if (i < grid.length - 1) down = helper(grid, i + 1, j, minSum);
	        if (j < grid[0].length - 1) right = helper(grid, i, j + 1, minSum);
	        minSum[i][j] = Math.min(right, down) + grid[i][j];
	        return minSum[i][j];
	    }
	 
}
