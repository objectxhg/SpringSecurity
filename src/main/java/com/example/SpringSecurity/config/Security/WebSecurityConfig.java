package com.example.SpringSecurity.config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @create 2021/3/30 15:42
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Resource
    public MySecurityAccountService securityService;

//    @Autowired
//    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(securityService)
                //springSecurity升级到5.x后,推荐BCryptPasswordEncoder方法加密
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 开启跨域共享，跨域伪造请求限制=无效
                .cors().disable()
                .csrf().disable()
                // 认证失败处理类
//                .exceptionHandling().authenticationEntryPoint(authenticationTokenFilter).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 验证码captchaImage 允许匿名访问
//                .antMatchers("/login", "/captchaImage").anonymous()
                .antMatchers("/login").anonymous()
                .antMatchers("/home").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
//        http.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);


        // 添加JWT filter
        http.addFilterBefore(accountAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
//        http.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
//        http.addFilterBefore(corsFilter, LogoutFilter.class);

    }


    /**
     * 账号登录认证过滤器
     * @return
     */
    @Bean
    public AccountAuthenticationFilter accountAuthenticationFilter(){
        AccountAuthenticationFilter accountAuthenticationFilter = new AccountAuthenticationFilter();
        accountAuthenticationFilter.setAuthenticationManager(authenticationManager);
        return accountAuthenticationFilter;
    }


    /**
     * 注册Bean
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 默认的账号登录认证策略
     * @return
     */
    @Bean
    public AbstractUserDetailsAuthenticationProvider usernameAndPasswordAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(securityAccountService());
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());//设置密码策略
        return daoAuthenticationProvider;
    }

    /**
     * 注册Bean
     * @return
     */
    @Bean
    public MySecurityAccountService securityAccountService(){
        return new MySecurityAccountService();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}

