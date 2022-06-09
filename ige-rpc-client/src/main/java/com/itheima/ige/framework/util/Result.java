package com.itheima.ige.framework.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    //是否成功
    private boolean flag;

    //状态码
    private Integer code;

    //返回消息
    private String message;

    //返回数据
    private T data;

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = (T) data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public static Result ok(ResultCode resultCode) {
        Result result = new Result();
        result.flag = true;
        result.code = resultCode.getCode();
        result.message = resultCode.getMsg();
        return result;
    }

    public static Result ok() {
        Result result = new Result();
        result.flag = true;
        result.code = ResultCode.SUCCESS.getCode();
        result.message = ResultCode.SUCCESS.getMsg();
        return result;
    }

    public static Result ok(Object data) {
        Result result = new Result();
        result.flag = true;
        result.data=data;
        result.code = ResultCode.SUCCESS.getCode();
        result.message = ResultCode.SUCCESS.getMsg();
        return result;
    }

    public static Result error(ResultCode resultCode) {
        Result result = new Result();
        result.flag = false;
        result.code = resultCode.getCode();
        result.message = resultCode.getMsg();
        return result;
    }

    public static Result error(ResultCode resultCode,Object data) {
        Result result = new Result();
        result.flag = false;
        result.code = resultCode.getCode();
        result.message = resultCode.getMsg();
        result.data=data;
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.flag = false;
        result.code = ResultCode.FAIL.getCode();
        result.message = ResultCode.FAIL.getMsg();
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.flag = false;
        result.code = ResultCode.FAIL.getCode();
        result.message = msg;
        return result;
    }

    public Result() {
        this.flag = true;
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMsg();
    }
}