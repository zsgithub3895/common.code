package com.zs.algorithm.gold;

import java.util.Arrays;

public class Gold {

	public static void main(String[] args) {
		int n = 5;//金矿数
		int w = 10;//人工数
		int[] g = {400,500,200,300,350};//每个金矿对应的黄金量
		int[] p = {5,  5,  3,  4,   3};///每个金矿对应的人工数(至少)
		System.out.println(getMostGold(5,10,g,p));
	}
	
	public static int getMostGold(int n,int w,int[] g,int[] p){
		int[] preResults = new int[w];
		int[] results = new int[w];
		
		//填充各边界格子的值
		for(int i = 0;i < w;i++){
			if(i < p[0]-1){
				preResults[i] = 0;
			}else{
				preResults[i] = g[0];
			}
		}
		
		System.out.println(Arrays.toString(preResults));
		System.out.println("+++++++++++++++++++++++++++++++++++++++++");
		//填充其余格子的值，外层循环是金矿数量，内层是人工数
		for(int i=1;i < n;i++){
			for(int j=0;j < w;j++){
				if(j+1 < p[i]){
					results[j] = preResults[j];
				}else{
					if(i==4){
						System.out.println("111+++++++++++++++++++++++++++++++++++++++++");
						System.out.println(Arrays.toString(preResults));
						System.out.println("111+++++++++++++++++++++++++++++++++++++++++");
					}
				    int personNum = j+1-p[i];
				    int index = 0;
				    if(personNum >= p[i]-1){
				    	index = personNum - 1;
				    }
					results[j] = Math.max(preResults[j],preResults[index]+g[i]);
				}
			}
			preResults = results;
			System.out.println(Arrays.toString(preResults));
		}
		return results[9];
	}

}
