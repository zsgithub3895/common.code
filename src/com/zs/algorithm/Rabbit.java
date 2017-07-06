package com.zs.algorithm;

/**
 * 首先我们要明白题目的意思指的是每个月的兔子总数(这里应该是按对来计算的)；
 * 我们假设将兔子分为小中大三种，兔子从出生后每三个月就生出一对兔子，那么我们假定第一个月为小兔子，
 * 第二个月为中兔子，第三个月之后就为老兔子(老兔子每过三个月还会再生的)，
 * 那么第一个月分别有1、0、0，第二个月分别为0、1、0，第三个月分别为1、0、1，
 * 第四个月分别为,1、1、1，第五个月分别为2、1、2，第六个月分别为3、2、3， 第七个月分别为5、3、5……
 * 兔子总数分别为：1、1、2、3、5、8、13……
 * 
 */
public class Rabbit {
	public static void main(String[] args) {
		System.out.println(getNumberMonth(5));
		System.out.println(getNumberMonth_other(5));
	}

	/**方法一*/
	public static int getNumberMonth(int month) {
		int init_rabbit = 1;
		if (month == 1 || month == 2) {
			return init_rabbit;
		}
		return getNumberMonth(month - 1) + getNumberMonth(month - 2);
	}

	/**方法二*/
	public static long getNumberMonth_other(int month){
			  long f1 = 1L, f2 = 1L;
			  long f;
			  //1 0 0,0 1 0,0 0 1-->1 0 1,1 1 1
			  for(int i=3; i<=month; i++) {
			    f = f2;
			    f2 = f1 + f2;
			    f1 = f;
			   // 1 1 2 3 5 8
			    System.out.println("第" + i +"个月的兔子对数: "+ f2);
			   }
			   return  f2;
	}
		
}
