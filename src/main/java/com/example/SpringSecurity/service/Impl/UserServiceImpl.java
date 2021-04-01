package com.example.SpringSecurity.service.Impl;

import com.example.SpringSecurity.config.Security.SecurityUtil;
import com.example.SpringSecurity.mapper.UserInfoMapper;
import com.example.SpringSecurity.pojo.UserInfo;
import com.example.SpringSecurity.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @create 2021/4/1 10:45
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public Integer insertUserService(String userName, String password) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);

        System.out.println(userInfo);

        String passHash = SecurityUtil.encodePassword(password);

        userInfo.setPassWord(passHash);

        return userInfoMapper.insert(userInfo);
    }
}

