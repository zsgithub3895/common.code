package com.zs.algorithm.design;

/**
 * @author zhangsai
 *	该实现方式有几个值得注意的技术点：
	使用双重锁检测机制，确保并发情况下instance对象不会被重复初始化。
	使用volatile修饰符，防止指令重排引发的初始化问题
	指令重排。

	指令重排是什么意思呢？比如java中简单的一句 instance = new Singleton，会被编译器编译成如下JVM指令：
	memory =allocate();    //1：分配对象的内存空间 
	ctorInstance(memory);  //2：初始化对象 
	instance =memory;     //3：设置instance指向刚分配的内存地址 
	
	但是这些指令顺序并非一成不变，有可能会经过JVM和CPU的优化，指令重排成下面的顺序：
	memory =allocate();    //1：分配对象的内存空间 
	instance =memory;     //3：设置instance指向刚分配的内存地址 
	ctorInstance(memory);  //2：初始化对象 
 */
public class Singleton {
    private Singleton() {}  //私有构造函数
    private volatile static Singleton instance = null;  //单例对象
    //静态工厂方法
    public static Singleton getInstance() {
          if (instance == null) {    //双重检测机制
	         synchronized (Singleton.class){  //同步锁
	           if (instance == null) {     //双重检测机制
	             instance = new Singleton();
	                }
	             }
          }
          return instance;
      }
    public void otherMethods(){
        System.out.println("双重检测机制实现单例");
    }
}

/**
 * @author zhangsai
 *	用静态内部类实现单例模式
 */
/*public class Singleton {
    private static class LazyHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton (){}
    public static Singleton getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    public void otherMethods(){
        System.out.println("静态内部类实现单例模式");
    }
}*/
