package com.example.SpringSecurity.config.Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author Administrator
 * @create 2021/3/30 15:13
 */
@ControllerAdvice
public class ExceptionSecurity {

    private static final Logger logger = LogManager.getLogger(ExceptionSecurity.class);

    @ExceptionHandler(value = AccessDeniedException.class)
    public String AccessDeniedExceptionHandler(AccessDeniedException exception) {
//        logger.info("捕获权限验证异常信息===》》》"+exception.getMessage());

        logger.info("SecurityException ----->"+exception.getMessage());

        return "403";
    }
}

