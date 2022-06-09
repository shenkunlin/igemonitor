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
 * 全量静态化配置
 * </p>
 * @author 作者
 * @since 2022-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("full_statically")
@ApiModel(value="FullStatically", description="全量静态化配置")
public class FullStatically implements Serializable {

    @ApiModelProperty(value = "主键ID，增加时，不需要，修改时，需要")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "任务名称[50字符以内]")
    @TableField("job_name")
    private String jobName;

    @ApiModelProperty(value = "业务唯一ID号[200字符以内]")
    @TableField("unique_id")
    private String uniqueId;

    @ApiModelProperty(value = "模板内容[不限制长度]")
    @TableField("template")
    private String template;

    @ApiModelProperty(value = "输出类型   1:输出本地  2:返回文件内容[1字符以内]")
    @TableField("out_type")
    private Integer outType;

    @ApiModelProperty(value = "存储类型")
    @TableField("storage_type")
    private String storageType;

    @ApiModelProperty(value = "输出本地的路径，只有out_type=1时，需要该值[200字符以内]")
    @TableField("local_path")
    private String localPath;

    @ApiModelProperty(value = "接收模板数据的远程地址，只有out_type=2时，需要该值[200字符以内]")
    @TableField("remote_url_receive")
    private String remoteUrlReceive;

    @ApiModelProperty(value = "命名规则属性,[输出类型=1时，才需要[100字符以内]]")
    @TableField("naming_key")
    private String namingKey;

    @ApiModelProperty(value = "命名空间ID[60字符以内]")
    @TableField("namespace_id")
    private String namespaceId;

    @ApiModelProperty(value = "命名空间名字[100字符以内]")
    @TableField("namespace_name")
    private String namespaceName;

    @ApiModelProperty(value = "执行时间,Cron表达式，例如，每5秒执行一次：0/5 * * * * ? ，参考地址：https://cron.qqe2.com/，[200字符以内]")
    @TableField("cron")
    private String cron;

    @ApiModelProperty(value = "是否启用   1：开启  2：关闭[1字符以内]")
    @TableField("status")
    private Integer status;
}
