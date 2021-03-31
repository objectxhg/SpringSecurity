package com.example.SpringSecurity.config.Security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.SpringSecurity.mapper.UserInfoMapper;
import com.example.SpringSecurity.pojo.UserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 3、实现UserDetailsService方法，自定义检查账户信息
 *
 * @author Administrator
 * 自定义账户认证方法，
 * 读取账户角色信息
 *
 */
@Service
public class MySecurityAccountService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(MySecurityAccountService.class);

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        /**
         * 根据账号查询数据库用户信息
         */
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);

        UserInfo user = userInfoMapper.selectOne(queryWrapper);

        logger.info("【MySecurityAccountService】:" + user);



        if(null == user){
            throw new UsernameNotFoundException("登陆账户不存在！");
        }
        /**
         * null: 用户角色
         */
        return user;
    }

    private List<SimpleGrantedAuthority> createAuthorities(String roleStr){
        String[] roles = roleStr.split(",");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthorities;
    }
}

