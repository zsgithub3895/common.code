package com.zs.leetcode.string;

import java.util.Arrays;

public class ZigZag {

	public static void main(String[] args) {
		System.out.println(convert("PAYPALISHIRING", 3));

	}

	public static String convert(String s, int numRows) {
		if (s.length() == 0 || numRows <= 1) return s;
		String res = "";
		String[] temp = new String[numRows];
		Arrays.fill(temp, "");
		int row = 0;
		int flag = 1;// 1表示从上向下，-1表示从下向上
		for (int i = 0; i < s.length(); i++) {
			temp[row] += s.charAt(i);
			row += flag;
			if(row >= numRows){
				row = numRows - 2;
				flag = -1;
			}
			
			if(row < 0){
				row = 1;
				flag = 1;
			}
		}
		
		for(int i=0;i<temp.length;i++){
			res+=temp[i];
		}
		return res;
	}

}
