package com.zs.leetcode.string;

public class ValidNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isNumber("2e10"));
	}
	
    public static boolean isNumber(String s) {
    	 if(s.trim().isEmpty()){
             return false;  
         }  
         String regex = "[-+]?(\\d+\\.?|\\.\\d+)\\d*(e[-+]?\\d+)?";  
         return s.trim().matches(regex);
    }
    
    public static boolean isNumber_two(String s) {
   	 if(s.trim().isEmpty()){
            return false;  
        }  
        String regex = "[-+]?(\\d+\\.?|\\.\\d+)\\d*(e[-+]?\\d+)?";  
        return s.trim().matches(regex);
   }

}
