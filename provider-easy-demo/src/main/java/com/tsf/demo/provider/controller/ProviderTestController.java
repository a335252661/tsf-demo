package com.tsf.demo.provider.controller;

import brave.Tracing;
import com.tsf.demo.provider.config.ProviderNameConfig;
import com.tsf.demo.provider.untils.IpUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderTestController {
  private static final Logger LOG = LoggerFactory.getLogger(ProviderController.class);
  
  @Autowired
  private ProviderNameConfig providerNameConfig;
  
  @Autowired
  private Tracing tracing;
    @Value("${spring.application.name:}")
  private String CurrentName;

  @RequestMapping({"/test1/findList0/{param}"}) public String echo010(@PathVariable String param) {return param+" , spring.application.name: "+ CurrentName +", 当前服务器ip："+ IpUntil.getIp();}
  @RequestMapping({"/test1/findList1606/{param}"}) public String echo011606(@PathVariable String param) {return param+" , spring.application.name: "+ CurrentName +", 当前服务器ip："+ IpUntil.getIp();}
}
