package com.chinatelecom.knowledgebase.filter;

import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Denny
 * @Date 2024/5/6 15:33
 * @Description
 * @Version 1.0
 */
@Slf4j
@Configuration
@WebFilter(filterName = "customHeaderFilter", urlPatterns = "/*")
public class AccessFilter implements Filter {

    //先只过滤一个最重要的，就是游客和普通用户不能查看待审核的问题

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截到了请求");
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;

        final String authorizationHeader = httpRequest.getHeader("Authorization");

        String username = null;
        String role=null;
        String jwt = null;


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // 移除 "Bearer " 前缀
            try {
                username = JwtUtils.extractUsername(jwt); // jwtTokenUtil 是用于解析JWT的工具类
                role=JwtUtils.extractRole(jwt);
            } catch (Exception e) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置HTTP状态码为401 Unauthorized
                httpServletResponse.setContentType("application/json;charset=UTF-8"); // 设置响应内容类型
                PrintWriter writer = servletResponse.getWriter();
                writer.print(JSONObject.toJSONString(R.error("登录失效，请重新登录"))); // 假设R.error是封装错误信息的方法，这里用JSON格式返回错误信息
                writer.flush();
                writer.close();
                return; // 结束方法执行，不再调用chain.doFilter(request, response);
            }
        }
        System.out.println("username:"+username);
        System.out.println("role:"+role);


//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // 验证JWT有效性，加载用户信息等
//            // ...
//        }
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }


}
