package com.chinatelecom.knowledgebase.config;

/**
 * @Author Denny
 * @Date 2024/3/19 14:37
 * @Description 设置url映射视频文件的位置
 * @Version 1.0
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    //开发环境下视频存放的地址
    @Value("${upload.video.directory}")
    private String videoUploadDirectory;
    @Value("${upload.image.directory}")
    private String imageUploadDirectory;

//这个配置文件说来完全没有必要，只是把对http://localhost:8088/videos/**的所有类型的请求转换到videoUploadDirectory
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 使用Environment变量或application.properties中的属性来获取实际路径
        registry.addResourceHandler("/videos/**")
                .addResourceLocations("file:" + videoUploadDirectory);

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageUploadDirectory);
    }
}