package com.tsf.demo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.tsf.annotation.EnableTsf;
import org.springframework.tsf.auth.annotation.EnableTsfAuth;

@SpringBootApplication
@EnableFeignClients // 使用Feign微服务调用时请启用
@EnableTsf
@EnableTsfAuth   //开启tsf鉴权
public class ProviderApplicationEasy {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplicationEasy.class, args);
    }
}