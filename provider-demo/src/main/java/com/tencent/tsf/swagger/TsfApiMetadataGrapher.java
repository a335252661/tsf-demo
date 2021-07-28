//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tencent.tsf.swagger;

import io.swagger.models.Swagger;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.tsf.core.util.TsfGzipUtil;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;


public class TsfApiMetadataGrapher implements SmartLifecycle {
    private Logger logger = LoggerFactory.getLogger(TsfApiMetadataGrapher.class);
    private ApplicationContext applicationContext;
    private ServiceModelToSwagger2Mapper swagger2Mapper;
    private DocumentationCache documentationCache;
    private JsonSerializer jsonSerializer;
    private String groupName;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public TsfApiMetadataGrapher(DocumentationCache documentationCache, ServiceModelToSwagger2Mapper swagger2Mapper, JsonSerializer jsonSerializer, String groupName, ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.swagger2Mapper = swagger2Mapper;
        this.documentationCache = documentationCache;
        this.jsonSerializer = jsonSerializer;
        this.groupName = groupName;
    }

    public boolean isAutoStartup() {
        return true;
    }

    public void stop(Runnable runnable) {
        runnable.run();
        this.stop();
    }

    public void start() {
        System.out.println("TsfApiMetadataGrapher------------------");
        if (this.isRunning.compareAndSet(false, true)) {
            try {
                Documentation documentation = this.documentationCache.documentationByGroup(this.groupName);
                Swagger swagger = this.swagger2Mapper.mapDocumentation(documentation);
                Json json = this.jsonSerializer.toJson(swagger);
                if (swagger != null && json != null && json.value() != null) {
//                    File file = new File("d:\\api.json");
                    File file = new File("/root/api.json");
                    if(!file.exists()) {
                        file.createNewFile();
                    }
                    try(FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(json.value().getBytes());
                    }
                    String serviceApiMeta = TsfGzipUtil.compressBase64Encode(json.value(), "utf-8");
                    ConfigurableEnvironment environment = ((ConfigurableApplicationContext)this.applicationContext).getEnvironment();
                    String applicationName = environment.getProperty("spring.application.name");
                    environment.getSystemProperties().put(String.format("%s$%s", applicationName, "api_metas"), serviceApiMeta);
                }
            } catch (Throwable var7) {
                this.logger.error("[tsf swagger] init tsfApiMetadataGrapher failed. occur exception: ", var7);
            }

        }
    }

    public void stop() {
        this.isRunning.set(true);
    }

    public boolean isRunning() {
        return this.isRunning.get();
    }

    public int getPhase() {
        return 0;
    }
}
