package com.itheima.ige.monitor.interceptor;

import com.alibaba.fastjson.JSON;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.framework.util.ResultCode;
import com.itheima.ige.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author：
 * @Description：
 ***/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!StringUtils.isEmpty(token)){
            try {
                JwtUtil.parse(token);
                return true;
            } catch (Exception e) {
            }
        }

        String uri = request.getRequestURI();
        if(uri.equals("/")){
            response.sendRedirect("/index.html");
            return false;
        }

        //提示登录
        Result result = Result.error(ResultCode.GOLOGIN);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.getWriter().print(JSON.toJSONString(result));
        return false;
    }
}
