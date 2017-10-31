package com.zs.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  把棋盘存储为一个N维数组a[N]，数组中第i个元素的值代表第i行的皇后位置，
 *  这样便可以把问题的空间规模压缩为一维O(N)，在判断是否冲突时也很简单，
 *  首先每行只有一个皇后，且在数组中只占据一个元素的位置，行冲突就不存在了，
 *  其次是列冲突，判断一下是否有a[i]与当前要放置皇后的列j相等即可。
 *  至于斜线冲突，通过观察可以发现所有在斜线上冲突的皇后的位置都有规律即它们所在的行列互减的绝对值相等，
 *  即| row – i | = | col – a[i] | 。这样某个位置是否可以放置皇后的问题已经解决。
 *
 */
public class NQueens {

	/**
	[".Q..",//A[0]=1  A[1]=0 , A[2] = 1
	 "...Q",
	 "Q...",//
	 "..Q."]  A[] = [1,3,0,2]=>A[0]=1,A[1]=3,A[2]=0,A[3]=2
	 */
	public static void main(String[] args) {
		solveNQueens(8);
	}

	public static List<List<String>> solveNQueens(int n) {
		List<List<String>> res = new ArrayList<List<String>>();
		if(n<=0){
			return res;
		}
		int[] colVal = new int[n];
		dfsHelper(n,res,0,colVal);
		System.out.println(Arrays.deepToString(res.toArray()));
		System.out.println(res.size());
		return res;
	}

	private static void dfsHelper(int queens,List<List<String>> res,int row,int[] colVal) {
	  if(row == queens){
		  List<String> list = new ArrayList<String>();
		  for(int i=0;i<queens;i++){
			  StringBuffer sb = new StringBuffer();
			  for(int j=0;j<queens;j++){
				  if(colVal[i] == j){
					  sb.append("Q");
				  }else{
					  sb.append(".");
				  }
			  }
			  list.add(sb.toString());
		  }
		  res.add(list);
	  }else{
		  for(int i=0;i<queens;i++){
			  colVal[row] = i;
			  if(isValid(row,colVal)){
				  dfsHelper(queens,res,row+1,colVal);
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
