package com.zs.leetcode.array;

public class NQueen {
	 static int total = 0;
	public static void main(String[] args) {
		System.out.println(totalNQueens(1));
	}
	
	 public static int totalNQueens(int n) {
			if(n<=0){
				return 0;
			}
			int[] colVal = new int[n];
	       
			dfsHelper(n,0,colVal);
			return total;
		}

		private static void dfsHelper(int queens,int row,int[] colVal) {
		  if(row == queens){
			 total++;
		   }else{
			  for(int i=0;i<queens;i++){
				  colVal[row] = i;
				  if(isValid(row,colVal)){
					  dfsHelper(queens,row+1,colVal);
				  }
			  }
		  }
			
		}

		private static boolean isValid(int row, int[] colVal) {
		    for (int i = 0; i < row; ++i)   //对棋盘进行扫描  
		    {  
		        if (colVal[row] == colVal[i] || Math.abs(colVal[row] - colVal[i]) == row -i)   //判断列冲突与斜线上的冲突  
		            return false;  
		    }  
			return true;
		}

}
