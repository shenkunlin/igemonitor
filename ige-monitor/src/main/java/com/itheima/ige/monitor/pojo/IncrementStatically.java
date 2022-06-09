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
 * 增量静态化配置
 * </p>
 * @author 作者
 * @since 2022-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("increment_statically")
@ApiModel(value="IncrementStatically", description="增量静态化配置")
public class IncrementStatically implements Serializable {

    @ApiModelProperty(value = "主键ID[60字符以内]")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "任务名称[50字符以内]")
    @TableField("job_name")
    private String jobName;

    @ApiModelProperty(value = "业务唯一识别码[200字符以内]")
    @TableField("unique_id")
    private String uniqueId;

    @ApiModelProperty(value = "模板内容[不限制 长度]")
    @TableField("template")
    private String template;

    @ApiModelProperty(value = "输出类型   1:输出本地  2:原路返回文件内容[1字符以内]")
    @TableField("out_type")
    private Integer outType;

    @ApiModelProperty(value = "存储类型   0：原路返回   1:输出本地  2:阿里云存储   3：IgeStorage存储   [1字符以内]")
    @TableField("storage_type")
    private String storageType;

    @ApiModelProperty(value = "输出本地的路径，只有out_type=1时，需要该值[200字符以内]")
    @TableField("local_path")
    private String localPath;

    @ApiModelProperty(value = "命名规则属性,[输出类型=1时，才需要][100字符以内]")
    @TableField("naming_key")
    private String namingKey;

    @ApiModelProperty(value = "命名空间ID[60字符以内]")
    @TableField("namespace_id")
    private String namespaceId;

    @ApiModelProperty(value = "命名空间名字[100字符以内]")
    @TableField("namespace_name")
    private String namespaceName;

    @ApiModelProperty(value = "触发方式：1：主动触发，2：MQ[1字符以内]")
    @TableField("trigger_method")
    private Integer triggerMethod;

    @ApiModelProperty(value = "MQ地址，只有trigger_method=2时，才有该值[500字符以内]")
    @TableField("trigger_mq_addr")
    private Integer triggerMqAddr;

    @ApiModelProperty(value = "MQ队列，只有trigger_method=2时，才有该值[100字符以内]")
    @TableField("trigger_mq_queue")
    private Integer triggerMqQueue;

    @ApiModelProperty(value = "是否启用   1：开启  2：关闭[1字符以内]")
    @TableField("status")
    private Integer status;
}
