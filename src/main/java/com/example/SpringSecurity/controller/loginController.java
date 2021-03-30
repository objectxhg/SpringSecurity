package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.config.Security.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Administrator
 * @create 2021/3/30 10:58
 */
@RestController
public class loginController {

    @GetMapping("/home")
    public String home() {

        return "This is home page";
    }


    @GetMapping("/login")
    public String LoginController(String username, String pwd){



        return "成功";
    }

}

