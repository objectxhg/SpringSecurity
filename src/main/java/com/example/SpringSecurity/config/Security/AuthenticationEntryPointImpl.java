package com.example.SpringSecurity.config.Security;

import com.alibaba.fastjson.JSON;
import com.example.SpringSecurity.utils.ServletUtils;
import com.example.SpringSecurity.vo.JsonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import com.example.SpringSecurity.utils.text.StringUtils;

/**
 * @Author Administrator
 * @create 2021/3/30 16:48
 */
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        int code = 201;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(JsonResult.fail(code, msg)));

    }
}

