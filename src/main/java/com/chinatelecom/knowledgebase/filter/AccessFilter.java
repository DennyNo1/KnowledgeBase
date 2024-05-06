package com.chinatelecom.knowledgebase.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author Denny
 * @Date 2024/5/6 15:33
 * @Description
 * @Version 1.0
 */
@Slf4j
@Configuration
@WebFilter(filterName = "multiUrlFilter", urlPatterns = {"/user/login", "/admin/dashboard"})
public class AccessFilter implements Filter {

    //先只过滤一个最重要的，就是游客和普通用户不能查看待审核的问题

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截到了请求");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }


}
