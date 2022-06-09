package com.itheima.ige.framework.exception;

import com.itheima.ige.framework.util.Result;
import com.itheima.ige.framework.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

/*****
 * @Author: 黑马训练营
 * @Description: com.changgou.framework.exception
 ****/
@ControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /***
     * 异常处理
     * 当前请求发生了指定异常，则执行该方法处理异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception ex){
        //异常记录
        log.error("全集异常捕获",ex);

        //异常返回
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        ex.printStackTrace(writer);
        ex.printStackTrace();
        return new Result(false, ResultCode.FAIL.getCode(),ex.getMessage(),stringWriter.toString());
    }

    /***
     * 后台校验问题处理
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result validException(MethodArgumentNotValidException ex){
        BindingResult bindingR = ex.getBindingResult();
        //异常处理
        StringBuffer buffer = new StringBuffer();
        for (FieldError fieldError : bindingR.getFieldErrors()) {
            buffer.append(fieldError.getDefaultMessage()+"  ");
        }
        return Result.error(buffer.toString());
    }
}
