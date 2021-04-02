package com.example.SpringSecurity.controller;


import com.example.SpringSecurity.config.Security.service.SecurityTokenService;
import com.example.SpringSecurity.exception.BaseException;
import com.example.SpringSecurity.pojo.UserInfo;
import com.example.SpringSecurity.service.UserService;
import com.example.SpringSecurity.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @create 2021/3/30 10:58
 */
@RestController
public class loginController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityTokenService securityTokenService;

    @Resource
    private UserService userService;


    @PostMapping("/home")
    public JsonResult home() {



        return JsonResult.success("成功");
    }


    @PostMapping("/reg")
    public JsonResult home(String username, String password) {

        Integer state = userService.insertUserService(username, password);

        if(state == 0) JsonResult.fail("失败");

        return JsonResult.success("成功");
    }


    @PostMapping("/login")
    public JsonResult LoginController(@RequestBody UserInfo formUser){

        System.out.println("formUser:" + formUser);

        Authentication authentication = null;
        try {
            // 该方法会去调用实现了的security登陆验证接口的MySecurityAccountService类
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formUser.getUsername(), formUser.getPassword()));

        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new BaseException(500, "账号/密码错误");
            }else{
                throw new BaseException(500, e.getMessage());
            }
        }

        UserInfo user = (UserInfo) authentication.getPrincipal();
        /**
         * 生成令牌
         */
        String token = securityTokenService.createToken(user);

        System.out.println("【登录成功 token:】" + token);

        return JsonResult.success(token);
    }

}

