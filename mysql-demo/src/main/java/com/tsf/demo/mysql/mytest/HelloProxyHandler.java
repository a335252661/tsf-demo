package com.tsf.demo.mysql.mytest;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2021/5/27
 */
public class HelloProxyHandler implements InvocationHandler {

    private Object target;

    public Object getProxyInstance(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        //在真实的对象执行之前我们可以添加自己的操作
        System.out.println("在真实的对象执行之前我们可以添加自己的操作");
        // JVM通过这条语句执行原来的方法(反射机制)
        result = method.invoke(this.target, args);

        //在真实的对象执行之后我们可以添加自己的操作
        System.out.println("after invoke。。。");

        return result;
    }

    public static void main(String[] args) {
        HelloProxyHandler doProxyHandler = new HelloProxyHandler();
        IHello proHello = (IHello)doProxyHandler.getProxyInstance(new Hello());
        proHello.say("2");
    }
}
