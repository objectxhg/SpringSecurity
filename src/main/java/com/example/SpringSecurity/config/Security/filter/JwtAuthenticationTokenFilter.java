package com.example.SpringSecurity.config.Security.filter;

import com.example.SpringSecurity.config.Security.JwtTokenUtil;
import com.example.SpringSecurity.config.Security.service.SecurityTokenService;
import com.example.SpringSecurity.pojo.UserInfo;
import com.example.SpringSecurity.utils.text.StringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1、token过滤器 验证token有效性
 *
 * 需要登录才能访问的接口 最先走这个过滤器 写验证jwt token的逻辑
 *
 * 验证通过后要告诉springsecurity，我们获取到的用户的权限，并把权限设置到springsecurity的上下文环境中，让它来给我们做权限的判断
 *
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private SecurityTokenService securityTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        /**
         * 通过token获取到存在redis用户信息
         */
        UserInfo user = securityTokenService.getLoginUser(request);

        if (StringUtils.isNotNull(user) && StringUtils.isNull(JwtTokenUtil.getAuthentication())) {
            securityTokenService.verifyToken(user);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        logger.info("【OncePerRequestFilter】：" + user);

        chain.doFilter(request, response);

    }
}
