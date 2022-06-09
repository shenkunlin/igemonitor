package com.itheima.ige.wrap.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author：
 * @Description：单个对象封装
 ***/
@Data
public class DataModel implements Serializable {

    //文件名
    private String fileName;

    //数据模型
    private Object data;
}
