package com.example.SpringSecurity.config.Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @create 2021/3/30 11:49
 */
//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        logger.info("=========================springSecurity初始化======================================");
        http
                .csrf()
                .disable()
                .authorizeRequests()
                //数据库角色表的角色码必须加ROLE_开头,例ROLE_USER
                .antMatchers("/index/**").hasRole("USER")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                //防止session篡改
                .sessionFixation().migrateSession()
                //Spring Security不会创建session或使用session(这里有四种模式,自由选择)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //session超时进入超时页面
                .invalidSessionUrl("/timeOut")
                //登陆配置信息
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/index")
                .permitAll()
                //权限不足时转向403页面
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                //配置登出处理
                .and()
                .logout()
                .invalidateHttpSession(true)
                .permitAll();

    }


    //=========================数据库账户认证======================================

//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
//        builder.userDetailsService(securityService)
//                //springSecurity升级到5.x后,推荐BCryptPasswordEncoder方法加密
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }

}

