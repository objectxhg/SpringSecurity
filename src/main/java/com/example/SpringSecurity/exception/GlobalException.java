package com.example.SpringSecurity.exception;

import com.example.SpringSecurity.vo.JsonResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

//@ControllerAdvice 可以配置basePackage下的所有controller
//原理是使用AOP对Controller控制器进行增强（前置增强、后置增强、环绕增强，AOP原理请自行查阅）

@ControllerAdvice
public class GlobalException {

	private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	/**
	 * @ExceptionHandler，可以处理异常
	 * 配合@ControllerAdvice 便可以处理全局异常
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JsonResult resultException(Exception e){

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(stream));
		String exception = stream.toString();
		logger.error("-----> Global-Exception： " + exception);

		return JsonResult.fail(e.getMessage());
	}

	@ExceptionHandler(BaseException.class)
	@ResponseBody
	public JsonResult resultException(BaseException e){

//		e.printStackTrace();
		logger.error("-----> Global-Exception： " + e.message);

		return JsonResult.fail(e.getCode(), e.getMessage());
	}

}
