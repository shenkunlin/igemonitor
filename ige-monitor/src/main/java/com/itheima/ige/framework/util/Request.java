package com.itheima.ige.framework.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author：
 * @date： 2022/3/13 7:33
 * @Description：
 ***/
@ApiModel(value = "请求分页信息封装",  description = "请求分页信息封装")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Request<T> implements Serializable {

    //当前页码
    @ApiModelProperty(notes = "当前页码", required = true)
    private Long page = 1L;
    //每页显示的行
    @ApiModelProperty(notes = "每页显示条数", required = true)
    private Long size = 10L;
    //请求体实体对象
    @ApiModelProperty(notes = "逻辑数据", required = true)
    private T body;
    //获取下标
    public Long getStart(){
        return (page-1)*size;
    }
}
