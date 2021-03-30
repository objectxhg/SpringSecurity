package com.example.SpringSecurity.exception;

import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;


/**
 * AllArgsConstructor使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
 *
 * @Accessors(chain = true) Accessor的中文含义是存取器，@Accessors用于配置getter和setter方法的生成结果
 *
 * 设置为true，则setter方法返回当前对象
 *
 */
@AllArgsConstructor
@Accessors(chain = true)
public class BaseException extends RuntimeException {

    protected Integer code;

    protected String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
