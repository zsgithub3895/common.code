package com.zs.leetcode.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterCombinations {

	public static void main(String[] args) {
		List<String> list = letterCombinations("234");
              for(String s:list){
            	  System.out.println(s);
              }
	}
	
	public static List<String> letterCombinations(String digits) {
		List<String> letter = new ArrayList<String>();
		if(null == digits || digits.length() == 0){
			return letter;
		}
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(0, "");
		map.put(1, "");
		map.put(2, "abc");
		map.put(3, "def");
		map.put(4, "ghi");
		map.put(5, "jkl");
		map.put(6, "mno");
		map.put(7, "pqrs");
		map.put(8, "tuv");
		map.put(9, "wxyz");
		ArrayList<Character> temp = new ArrayList<Character>();
		getString(digits, temp, letter, map);
		return letter;
    }
	
	public static void getString(String digits, ArrayList<Character> temp, List<String> result, Map<Integer, String> map){
	    if(digits.length() == 0){
	        char[] arr = new char[temp.size()];
	        for(int i=0; i<temp.size(); i++){
	            arr[i] = temp.get(i);
	        }
	        result.add(String.valueOf(arr));
	        return;
	    }
	 
	    Integer curr = Integer.valueOf(digits.substring(0,1));
	    String letters = map.get(curr);
	    for(int i=0; i<letters.length(); i++){
	        temp.add(letters.charAt(i));
	        getString(digits.substring(1), temp, result, map);
	        temp.remove(temp.size()-1);
	    }
	}

}
