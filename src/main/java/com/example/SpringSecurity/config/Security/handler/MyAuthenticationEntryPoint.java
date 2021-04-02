package com.example.SpringSecurity.config.Security.handler;

import com.alibaba.fastjson.JSON;
import com.example.SpringSecurity.utils.ServletUtils;
import com.example.SpringSecurity.utils.text.StringUtils;
import com.example.SpringSecurity.vo.JsonResult;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 */
@Configuration
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {

        System.out.println("请求访问：{}，认证失败，无法访问系统资源");

        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(JsonResult.fail(201, msg)));
    }

}

