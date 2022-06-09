package com.itheima.ige.wrap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfoConfig implements Serializable {

    //ID
    private String id;
    private String url;

    //具体地址
    private String fullurl;

    //1：FastDFS=IgeConstant.OUTTYPE_FASTDFS   2：AliyunOSS=IgeConstant.OUTTYPE_ALIYUNOSS
    private int type;

    // 服务配置
    private Map<String,String> config;
}
