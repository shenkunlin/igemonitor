package com.itheima.storage.ige;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "ige.storage")
public class IgeStorageConfig {

    private Map<String,String> paths;

    private String url;
}
