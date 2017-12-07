package com.zs.algorithm.design;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonTest {
//三种设计模式区别见图singleton.jpg
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		testSingle();
		//枚举单例测试
		SingletonEnum singleton1 = SingletonEnum.INSTANCE;
		singleton1.otherMethods();
	}
	
	public static void testSingle(){
				//利用反射打破单例：
				//获得构造器
				Constructor con;
				try {
					con = Singleton.class.getDeclaredConstructor();
					//设置为可访问
					con.setAccessible(true);
					//构造两个不同的对象
					Singleton singleton1 = (Singleton)con.newInstance();
					Singleton singleton2 = (Singleton)con.newInstance();
					//验证是否是不同对象
					singleton1.otherMethods();
					System.out.println(singleton1.equals(singleton2));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
	}

}
