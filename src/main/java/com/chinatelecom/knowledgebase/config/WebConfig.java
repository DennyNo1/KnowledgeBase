package com.chinatelecom.knowledgebase.config;

/**
 * @Author Denny
 * @Date 2024/3/19 14:37
 * @Description 设置url映射视频文件的位置
 * @Version 1.0
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    //开发环境下视频存放的地址
    @Value("${upload.video.path}")
    private String videoUploadPath;
    @Value("${upload.image.path}")
    private String imageUploadPath;
    @Value("${upload.attachment.path}")
    private String attachmentUploadPath;

    //把对http://localhost:8088/videos/**的所有类型的请求转换到videoUploadDirectory。就是把真实的路径，通过接口暴露出去。
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 使用Environment变量或application.properties中的属性来获取实际路径
        registry.addResourceHandler("/videos/**")
                .addResourceLocations("file:" + videoUploadPath);

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageUploadPath);

        registry.addResourceHandler("/attachments/**")
                .addResourceLocations("file:" + attachmentUploadPath);
    }
//    给所有接口加一层前缀/api，以区分ragflow的接口。除了上面三个。
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", c -> true);
    }
}