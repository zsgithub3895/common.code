package com.aaa.test;

import java.io.File;
import java.util.regex.Matcher;

public class Test {
	/**
	 *
	 */
	public static void main(String[] args) {
       /* for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                System.out.println("i="+i+"j="+j);
                if(j==3){
                    break;
                }
            }
        }*/
		//System.out.println("a\u0022.length()+\u0022b".length());//\u0022 表示双引号Unicode的转义字符
		//System.out.print("hell");
		//System.out.println("o world");
		/*System.out.println(Test.class.getName().replace(".", File.separator)+".class");
		System.out.println(Test.class.getName().replaceAll("\\.", Matcher.quoteReplacement(File.separator))+".class");
		System.out.println(Test.class.getName().replace('.', File.separatorChar)+".class");*/
		System.out.println(System.nanoTime());
	}

}
