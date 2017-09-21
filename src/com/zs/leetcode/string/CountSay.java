package com.zs.leetcode.string;

public class CountSay {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//n=1，1
		//n=2，11
		//n=3, 21
		//n=4,1211
		//n=5,111221
		//n=6,312211
		//n=7,13112221
		//n=8,1113213211
		//n=9,31131211131221
		System.out.println(countAndSay(4));
	}

	public static String countAndSay(int n) {
		        if (n <= 0)
		            return "";
		        if(n == 1)
		        	return "1";
		        String res = "1";
		        for (int i = 1; i < n; i++) {
		        	StringBuilder tmp = new StringBuilder();
		            int count = 1;
		            for (int j = 1; j < res.length(); j++) {
		                if (res.charAt(j) == res.charAt(j - 1)) {
		                    count++;
		                } else {
		                	tmp.append(count);
		                	tmp.append(res.charAt(j - 1));
		                    count = 1;
		                }
		            }
		           tmp.append(count);
		           tmp.append(res.charAt(res.length() - 1));
		            res = tmp.toString();
		        }
		        return res;
		    }
	
	public static String countAndSay_two(int n) {
        if(n == 0){
            return "";
        }
        if(n == 1){
            return "1";
        }
        String next_str = "1";
        for(int i=1; i<n; i++){
            next_str = generateNext(next_str);
        }
        return next_str;
    }
    
    private static String generateNext(String s){
        StringBuilder result = new StringBuilder();
        int cur = 0, next = 1;
        int count = 1;
        while(next < s.length()){
            if(s.charAt(next) == s.charAt(cur)){
                count++;
                next++;
            }
            else{
                result.append(count);
                result.append(s.charAt(cur));
                cur = next;
                next = cur+1;
                count = 1;
            }
        }
        result.append(s.length()-cur);
        result.append(s.charAt(cur));
        return result.toString();
    }
}
