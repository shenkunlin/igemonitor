package com.itheima.ige.monitor.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 服务配置
 * </p>
 * @author 作者
 * @since 2022-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("server_config")
@ApiModel(value="ServerConfig", description="服务配置")
public class ServerConfig implements Serializable {

    @ApiModelProperty(value = "组件ID，添加时，不需要该参数，修改时，必须携带")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "服务名字[200字符以内]")
    @TableField("server_name")
    private String serverName;

    @ApiModelProperty(value = "服务类型  1：Nacos   2：Http服务配置[1字符以内]")
    @TableField("server_type")
    private Integer serverType;

    @ApiModelProperty(value = "服务描述[500字符以内]")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "配置内容:" +
            "Nacos配置示例={ip:\"192.168.211.130\",port:8081,group:\"shop\"} " +
            "Http配置示例={address:\"http://www.itheima.com\",secret:\"dsfswe\"}[不限制长度]")
    @TableField("config_content")
    private String configContent;

    @ApiModelProperty(value = "是否启用  1：启用   2：关闭[1字符以内]")
    @TableField(value = "enable")
    private Integer enable;
}
