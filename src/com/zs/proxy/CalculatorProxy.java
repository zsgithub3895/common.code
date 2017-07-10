package com.zs.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
public class CalculatorProxy {
    /**
     * @param calculator传入被代理的对象
     * @return 返回的是代理对象
     * 被代理对象执行方法时实际是代理对象在帮他执行这个方法
     */
    public static Calculator getProxy(final Calculator calculator){
        //被代理对象的类加载器，
        ClassLoader classLoader = calculator.getClass().getClassLoader();
        //Class<?>[] 被代理对象所实现的所有接口
        Class<?>[] interfaces = calculator.getClass().getInterfaces();

        //InvocationHandler实际去执行被代理对象的方法的处理器
        InvocationHandler h = new InvocationHandler() {
            /**
             * Object proxy：代理对象
             * Method method：代理的方法，即将要执行的方法
             * Object[] args：执行方法的时候要用的参数
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {
                //获取方法名
                String name = method.getName();
                System.out.println("动态代理方法日志["+name+"方法开始执行]【参数："+Arrays.asList(args));
                //声明一个引用来接收方法返回值
                Object invoke = null;
                try {
                    //利用反射执行方法
                    invoke = method.invoke(calculator, args);
                    System.out.println("动态代理方法日志["+name+"方法执行结束]【返回值：】"+invoke);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("动态代理方法日志["+name+"方法执行异常]【异常信息：】"+e.getCause().getMessage());
                }
                System.out.println("动态代理方法日志["+name+"方法执行完成了】");
                //返回被代理对象这个方法最终执行的结果
                return invoke;
            }
        };

        //返回代理对象
        Calculator newProxyInstance = (Calculator) Proxy.newProxyInstance(classLoader, interfaces, h);
        return newProxyInstance;
    }
}

