///*
// * Copyright (c) 2018 www.tencent.com.
// * All Rights Reserved.
// * This program is the confidential and proprietary information of
// * www.tencent.com ("Confidential Information").  You shall not disclose such
// * Confidential Information and shall use it only in accordance with
// * the terms of the license agreement you entered into with www.tencent.com.
// */
//
//package com.tsf.demo.provider.swagger;
//
//import com.google.common.base.Predicate;
//import com.google.common.base.Predicates;
//import com.tencent.tsf.swagger.autoconfig.SwaggerContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.DocumentationCache;
//import springfox.documentation.spring.web.json.JsonSerializer;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author kysonli
// * @date 2018/9/17 11:27
// * @since 1.1.1
// */
//@EnableSwagger2
//@EnableWebMvc
//@Configuration
//@ConditionalOnClass(name = "org.springframework.web.servlet.config.annotation.EnableWebMvc")
//@ConditionalOnProperty(value = "tsf.swagger.enabled", havingValue = "true",matchIfMissing = true)
//public class TsfSwaggerAutoConfiguration {
//
//    private static Logger logger = LoggerFactory.getLogger(TsfSwaggerAutoConfiguration.class);
//
//    @Value("${spring.application.name:}")
//    private String applicationName;
//
//    @Value("${tsf.swagger.basePackage:}")
//    private String basePackage;
//
//    @Value("${tsf.swagger.excludePath:}")
//    private String excludePath;
//
//    @Value("${tsf.swagger.enabled:true}")
//    private boolean enabled;
//
//    @Value("${tsf.swagger.group:default}")
//    private String groupName;
//
//    @Value("${tsf.swagger.basePath:/**}")
//    private String basePath;
//
//    @Bean
//    public Docket tsfDocket(){
//        // exclude-path处理
//        List<Predicate<String>> excludePath = getExcludePaths();
//        if (StringUtils.isEmpty(basePackage)) {
//            String mainClass = String.valueOf(SwaggerContext.getAttribute(String.format("%s$%s",applicationName,"MainClass")));
//            try {
//                Class<?> clz = Class.forName(mainClass);
//                basePackage = clz.getPackage().getName();
//                logger.info("[tsf-swagger] application main class:{}, auto detect swagger scan package:{} ", mainClass, basePackage);
//            }catch (Throwable t) {
//                logger.warn("[tsf-swagger] swagger scan package is empty and auto detect main class occur exception. " +
//                        "tsf swagger component fail to start.");
//            }
//        }
//        List<Predicate<String>> basePathList = new ArrayList<Predicate<String>>();
//        if (StringUtils.isEmpty(basePath)) {
//            basePathList.add(PathSelectors.ant("/**"));
//        } else  {
//            String[] basePaths = getBasePath().split(",");
//            for (String basePath : basePaths) {
//                if (!StringUtils.isEmpty(basePath)) {
//                    basePathList.add(PathSelectors.ant(basePath));
//                }
//            }
//        }
//
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(basePackage))
//                .paths( Predicates.and(Predicates.not(Predicates.or(excludePath)),Predicates.or(basePathList)))
//                .build()
//                .groupName(groupName)
//                .enable(enabled)
//                .directModelSubstitute(LocalDate.class, java.util.Date.class)
//                .apiInfo(apiInfo());
//        return  docket;
//    }
//
//
//    @Bean
//    @ConditionalOnBean(value = {Docket.class})
//    public TsfApiMetadataGrapher tsfApiMetadataGrapher(DocumentationCache documentationCache,
//                                                       ServiceModelToSwagger2Mapper swagger2Mapper,
//                                                       JsonSerializer jsonSerializer,ApplicationContext context){
//        return new TsfApiMetadataGrapher(documentationCache,swagger2Mapper,jsonSerializer,groupName,context);
//    }
//
//
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("Swagger API")
//                .description("This is to show api description")
//                .license("Apache 2.0")
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//                .termsOfServiceUrl("")
//                .version(VERSION)
//                .contact(new Contact("","", ""))
//                .build();
//    }
//
//    private static final String VERSION = "1.0.0";
//
//    private List<Predicate<String>> getExcludePaths(){
//        List<Predicate<String>> excludes = new ArrayList<>();
//        if (excludePath == null) {
//            return  excludes;
//        }
//        String[] exs = excludePath.split(",");
//        Arrays.stream(exs).filter(ex -> !StringUtils.isEmpty(ex)).forEach(ex -> excludes.add(PathSelectors.ant(ex)));
//        return excludes;
//    }
//
//    public String getBasePackage() {
//        return basePackage;
//    }
//
//    public void setBasePackage(String basePackage) {
//        this.basePackage = basePackage;
//    }
//
//    public String getExcludePath() {
//        return excludePath;
//    }
//
//    public void setExcludePath(String excludePath) {
//        this.excludePath = excludePath;
//    }
//
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public String getGroupName() {
//        return groupName;
//    }
//
//    public void setGroupName(String groupName) {
//        this.groupName = groupName;
//    }
//
//    public String getBasePath() {
//        return basePath;
//    }
//
//    public void setBasePath(String basePath) {
//        this.basePath = basePath;
//    }
//}
