package com.itheima.storage.alioss;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AliyunConfig {

    @Value("${alioss.endpoint:https://oss-cn-hangzhou.aliyuncs.com}")
    private String endpoint;
    @Value("${alioss.access-key:null}")
    private String accessKey;
    @Value("${alioss.access-secret:null}")
    private String accessSecret;
    @Value("${alioss.bucket-name:null}")
    private String bucketName;
    @Value("${alioss.url:null}")
    private String url;
    @Value("${alioss.content-type:html/text}")
    private String contentType;
}
