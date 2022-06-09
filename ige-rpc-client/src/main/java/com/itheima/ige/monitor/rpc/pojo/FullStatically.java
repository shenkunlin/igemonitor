package com.itheima.ige.monitor.rpc.pojo;

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
public class FullStatically implements Serializable {

    private String id;

    private String jobName;

    private String uniqueId;

    private String template;

    private Integer outType;

    private String storageType;

    private String localPath;

    private String remoteUrlReceive;

    private String namingKey;

    private String namespaceId;

    private String namespaceName;

    private String cron;

    private Integer status;
}
