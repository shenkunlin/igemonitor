package com.itheima.ige.monitor.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 执行日志
 * </p>
 * @author 作者
 * @since 2022-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ige_log")
@ApiModel(value="IgeLog", description="执行日志")
public class IgeLog implements Serializable {


    @ApiModelProperty(value = "主键ID")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "日志内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "类型  1：普通操作日志   2：异常日志   3：全量操作日志  4：增量操作日志")
    @TableField("log_type")
    private Integer logType;

    @ApiModelProperty(value = "所属命名空间ID")
    @TableField("namespace_id")
    private String namespaceId;

    @ApiModelProperty(value = "所属命名空间")
    @TableField("namespace_name")
    private String namespaceName;
}
