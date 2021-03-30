package com.example.SpringSecurity.vo;

/**
 * @Author Administrator
 * @create 2021/1/11 15:21
 */

//@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonResult<T> {

    private static final Integer EXCEPTION_CODE = 500;

    private static final Integer SUCCESS_CODE = 200;

    private static final String SUCCESS_MESSAGE = "成功";

    private Integer code;

    private String message;

    private T data;

    public JsonResult() {
        super();
    }

    public JsonResult(Exception e) {
        this.code = EXCEPTION_CODE;
        this.message = e.getMessage();
    }

    public JsonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public JsonResult(Integer code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static JsonResult success(Object data) {

        return new JsonResult(SUCCESS_CODE, data, SUCCESS_MESSAGE);
    }

    public static JsonResult success(String message) {

        return new JsonResult(SUCCESS_CODE, message);
    }

    public static JsonResult success(Integer code, String message) {

        return new JsonResult(code, message);
    }

    public static JsonResult fail(String message) {

        return new JsonResult(EXCEPTION_CODE, message);
    }

    public static JsonResult fail(Integer code, String message) {

        return new JsonResult(code, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

