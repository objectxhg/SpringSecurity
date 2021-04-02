package com.example.SpringSecurity.config.Security.handler;

import com.alibaba.fastjson.JSON;
import com.example.SpringSecurity.config.Security.service.SecurityTokenService;
import com.example.SpringSecurity.pojo.UserInfo;
import com.example.SpringSecurity.utils.ServletUtils;
import com.example.SpringSecurity.utils.text.StringUtils;
import com.example.SpringSecurity.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 */
@Configuration
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private SecurityTokenService securityTokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        UserInfo user = securityTokenService.getLoginUser(request);
        if (StringUtils.isNotNull(user)) {
            String userName = user.getUsername();
            // 删除用户缓存记录
            securityTokenService.delLoginUser(user.getToken());
        }

        System.out.println("退出成功");

        ServletUtils.renderString(response, JSON.toJSONString(JsonResult.fail(200,"退出成功")));

    }

}

