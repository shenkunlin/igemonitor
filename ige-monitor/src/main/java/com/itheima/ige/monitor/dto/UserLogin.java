package com.itheima.ige.monitor.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author： itheima
 * @Description：登录
 ***/
@ApiModel(value="UserLogin", description="用户登录")
@Data
public class UserLogin implements Serializable {

    @ApiModelProperty(value = "用户名,长度为5-20位，且不为空")
    @NotNull(message = "用户名不允许为空！")
    @Length(min = 5,max = 20,message = "用户名长度为5-20位")
    private String username;

    @ApiModelProperty(value = "登录密码,长度为5-20位")
    @NotNull(message = "登录密码不允许为空！")
    @Length(min = 5,max = 20,message = "登录密码长度为5-20位")
    private String password;
}
