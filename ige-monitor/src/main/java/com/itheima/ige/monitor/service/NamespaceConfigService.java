package com.itheima.ige.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.pojo.NamespaceConfig;

/**
 * <p>
 * 命名空间配置 服务类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
public interface NamespaceConfigService extends IService<NamespaceConfig> {

    Result<NamespaceConfig> addNamespace(NamespaceConfig record);
}
