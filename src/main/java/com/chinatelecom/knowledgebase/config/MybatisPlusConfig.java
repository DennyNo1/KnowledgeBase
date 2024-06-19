package com.chinatelecom.knowledgebase.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Denny
 * @Date 2024/1/18 16:11
 * @Description
 * @Version 1.0
 */
@Configuration
public class MybatisPlusConfig
{
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() //MyBatisPlusInterceptor，拦截执行中的sql语句，然后添加分页功能
    {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
