package com.tsf.demo.provider.swagger;

/**
 * @author 程刘德
 * @version 1.0
 * @Description TODO
 * @date 2021/6/24
 */
public class cld {
    public static void main(String[] args) {

//        System.out.println(1);

        // provider$api_metas=xxxxxxxxxxxxx
        System.setProperty(String.format("%s$%s", "provider", "api_metas"), "xxxxxxxxxxxxx");
        System.out.println(System.getProperties());
    }
}
