package com.example.SpringSecurity.config.Security;

import com.alibaba.fastjson.JSON;
import com.example.SpringSecurity.utils.ServletUtils;
import com.example.SpringSecurity.vo.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 * @Author Administrator
 * @create 2021/3/30 16:48
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {

        logger.info("请求访问：{}，认证失败，无法访问系统资源",  request.getRequestURI());

        ServletUtils.renderString(response, JSON.toJSONString(JsonResult.fail(201, "认证失败")));

    }
}

