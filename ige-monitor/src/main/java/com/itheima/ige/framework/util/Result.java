package com.itheima.ige.framework.util;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/****
 * @Author:www.itheima.com
 * @Description:返回实体Bean
 * @Date  15:55
 *****/
@Data
@ApiModel(description = "Result",value = "Result")
public class Result<T> implements Serializable {
    //是否成功
    @ApiModelProperty(value="执行是否成功,true:成功,false:失败",required = true)
    private boolean flag;

    //状态码
    @ApiModelProperty(value="返回状态码," +
            "200:操作成功\n" +
            "201:操作失败\n" +
            "501:用户名或密码错误\n" +
            "502:两次密码不匹配\n" +
            "403:权限不足\n" +
            "404:远程调用失败\n" +
            "407:已存在要操作的数据",
            required = true)
    private Integer code;

    //返回消息
    @ApiModelProperty(value="提示信息")
    private String message;

    //返回数据
    @ApiModelProperty(value="逻辑数据")
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

    public String json(){
        return JSON.toJSONString(this);
    }
}
