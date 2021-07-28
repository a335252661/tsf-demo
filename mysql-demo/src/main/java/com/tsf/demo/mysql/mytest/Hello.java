package com.tsf.demo.mysql.mytest;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2021/5/27
 */
public class Hello implements IHello{
    @Override
    public void say(String tar) {
        System.out.println(tar);
    }
}
