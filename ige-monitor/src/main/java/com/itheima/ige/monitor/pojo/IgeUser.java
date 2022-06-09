package com.itheima.ige.monitor.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户信息
 * </p>
 * @author 作者
 * @since 2022-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ige_user")
@ApiModel(value="IgeUser", description="用户信息")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IgeUser implements Serializable {

    @ApiModelProperty(value = "主键ID，[60字符以内]")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "账号[5-20字符以内]")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码[5-20字符以内]")
    @TableField("password")
    private String password;
}
