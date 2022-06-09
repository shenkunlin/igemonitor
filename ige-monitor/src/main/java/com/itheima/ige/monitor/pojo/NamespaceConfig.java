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
 * 命名空间配置
 * </p>
 * @author 作者
 * @since 2022-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("namespace_config")
@ApiModel(value="NamespaceConfig", description="命名空间配置")
public class NamespaceConfig implements Serializable {


    @ApiModelProperty(value = "命名空间ID，增加时，不需要传入该值，修改时，必须传入[60字符以内]")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "命名空间名字，使用英文数字混合，允许有下划线，修改时，不允许修改该字段[100字符以内]")
    @TableField("namespace_name")
    private String namespaceName;

    @ApiModelProperty(value = "命名空间描述[500字符以内]")
    @TableField("description")
    private String description;
}
