package com.itheima.ige.template.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author：
 * @date： 2022/3/13 23:26
 * @Description：
 ***/
@Data
@ConfigurationProperties(prefix = "ige")
public class TemplateConfig {

    /**
     * 生成文件存储路径
     */
    private String dir;
}
