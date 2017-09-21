package com.zs.leetcode.array;

import java.util.Arrays;

public class SolveSudoku {

	public static void main(String[] args) {
		char[][] board = {
				"139748652".toCharArray(),
				"745692831".toCharArray(),
				"826159473".toCharArray(),
				"317986245".toCharArray(),
				"264317598".toCharArray(),
				"598421367".toCharArray(),
				"451863729".toCharArray(),
				"972534186".toCharArray(),
				"683275914".toCharArray(),
				};
		solveSudoku(board);
		System.out.println(isValidSudoku(board,2,5));
	}

	 public static void solveSudoku(char[][] board) {
	        if(board == null || board.length != 9 || board[0].length !=9)  
	    	        return;  
	    	if(helper(board,0,0)){
	    		System.out.println(Arrays.deepToString(board));
	    	};
	    }
	    
	    private static boolean helper(char[][] board,int i,int j){
	        if(i==9){
	           return true;
	        }
	        if(j>=9){
	            return helper(board,i+1,0);
	        }
	        if(board[i][j]=='.'){
	            for(int k=1;k<=9;k++){
	                board[i][j]=(char)(k+'0');
	                if(isValidSudoku(board,i,j) && helper(board,i,j+1)){
	                    return true;
	                }
	                board[i][j]='.';
	            }
	        }else{
	            return helper(board,i,j+1);
	        }
	        return false;
	    }
	    
	    
	    private static boolean isValidSudoku(char[][] board,int i,int j) {
			for(int k=0;k<board.length;k++)  
		    {
	            if(k!=i && board[k][j]==board[i][j]){
	                return false;
	            }
	        }
	        
	        for(int k=0;k<board.length;k++)  
		    {
	            if(k!=j && board[i][k]==board[i][j]){
	                return false;
	            }
	        }
	        
	        for(int row=(i/3)*3;row<(i/3+1)*3;row++){
	            for(int col=(j/3)*3;col<(j/3+1)*3;col++){ 
	                if((row!=i || col != j) && board[row][col]==board[i][j]){
	                    return false;
	                }
	            }
	        }
	         return true;
		    }
}
