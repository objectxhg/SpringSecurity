package com.example.SpringSecurity.config.Security.filter;

import com.example.SpringSecurity.config.Security.handler.MyAuthenticationSuccessHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2、
 *
 */
public class MyAccountAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LogManager.getLogger(MyAccountAuthenticationFilter.class);

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);

        logger.info("【MyAccountAuthenticationFilter】：" + username + "-" + password);

        if(username == null) {
            username = "";
        }

        if(password == null) {
            password = "";
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }


}

