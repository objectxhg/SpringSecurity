package com.example.SpringSecurity.controller;


import com.example.SpringSecurity.config.Security.service.SecurityTokenService;
import com.example.SpringSecurity.exception.BaseException;
import com.example.SpringSecurity.pojo.UserInfo;
import com.example.SpringSecurity.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/home")
    public String home() {



        return "This is home page";
    }


    @PostMapping("/login")
    public JsonResult LoginController(String username, String password){

        System.out.println(username + ":"+ password);

//        加密：
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        final String passHash = encoder.encode(pass);
//        验证：
//        boolean matches = encoder.matches(pass, passHash);


        Authentication authentication = null;
        try {
            // 该方法会去调用实现了的security登陆验证接口的MySecurityAccountService类
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new BaseException(500, e.getMessage());
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

        return JsonResult.success(user);
    }

}

