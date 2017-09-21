package com.zs.leetcode.array;

import java.util.Arrays;

public class ValidSudoku {

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
		//System.out.println(isValidSudoku(board));
	}
	
	public static boolean isValidSudoku(char[][] board) {
		 boolean[] map_row = new boolean[9]; 
	     boolean[] map_col = new boolean[9];
	     boolean[] map_only = new boolean[9];//每个小九宫格
		for(int i=0;i<board.length;i++)  
	    {
	        for(int j=0;j<board[0].length;j++)
	        {
	            if(board[i][j] != '.')  
	            {
	                if(map_row[(int)(board[i][j]-'1')])  
	                {  
	                    return false;
	                }  
	                map_row[(int)(board[i][j])-'1'] = true;  
	            }  
	            
	            if(board[j][i] != '.')  
	            {
	                if(map_col[(int)(board[j][i])-'1'])  
	                {  
	                    return false;
	                }  
	                map_col[(int)(board[j][i])-'1'] = true;  
	            } 
	            
	            if(board[i/3*3+j/3][i%3*3+j%3] != '.')
                {
                    if(map_only[(int)(board[i/3*3+j/3][i%3*3+j%3]-'1')]){
                    	 return false;
                    }
                       map_only[(int)(board[i/3*3+j/3][i%3*3+j%3]-'1')] = true;
                }
	        }  
	    }
		return true;
	}
	
	 public static boolean isValidSudoku_two(char[][] board) {
	        int[] b1 = new int[9];
	        int[] b2 = new int[9];
	        int[] b3 = new int[9];
	        for (int i = 0; i < board.length; ++i)
	        {
	            for (int j = 0; j < board[0].length; ++j)
	            {
	                if (board[i][j] != '.')
	                {
	                    int cur = board[i][j] - '1';
	                    System.out.println(b1[i]);
	                    if ((b1[i] & 1 << cur) != 0)
	                        return false;
	                    b1[i] |= 1 << cur;
	                    
	                    /*if ((b2[j] & 1 << cur) != 0)
	                        return false;
	                    b2[j] |= 1 << cur;
	                    int index = i / 3 * 3 + j / 3;
	                    if ((b3[index] & 1 << cur) != 0)
	                        return false;
	                    b3[index] |= 1 << cur;*/
	                }
	            }
	        }
	        return true;
	    }

}
