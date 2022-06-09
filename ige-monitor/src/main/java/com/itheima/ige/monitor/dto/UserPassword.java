package com.itheima.ige.monitor.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author：
 * @Description：修改密码
 ***/
@ApiModel(value="UserPassword", description="修改密码")
@Data
public class UserPassword implements Serializable {

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "修改密码操作，用户ID不允许为空！")
    private Integer id;

    @ApiModelProperty(value = "新密码")
    @NotNull(message = "新密码不允许为空！")
    @Length(min = 5,max = 20,message = "新密码长度为5-20位")
    private String password;

    @ApiModelProperty(value = "确认新密码，长度为5-20位")
    @NotNull(message = "确认新密码不允许为空！")
    @Length(min = 5,max = 20,message = "确认新密码长度为5-20位")
    private String rePassword;
}
