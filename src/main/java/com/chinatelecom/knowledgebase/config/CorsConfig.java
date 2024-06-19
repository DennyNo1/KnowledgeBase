package com.chinatelecom.knowledgebase.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Denny
 * @Date 2024/3/15 10:16
 * @Description 配置CORS策略，允许来自前端应用的请求
 * @Version 1.0
 */
@Slf4j//日志


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 允许所有来源
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // 允许所有请求方法
                .allowedHeaders("*"); // 允许所有请求头
    }
}
