package com.zs.regx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regx {

	public static void main(String[] args) {
		String str = "2017/05/22 19:10:22";
		Pattern p = Pattern.compile("[\\:\\/\\ ]");
		Matcher m = p.matcher(str); 
		String time = m.replaceAll("");
		System.out.println("++++++++++++++++++++++++"+time.substring(0, 10));//substring(0, 8)
	}

}
