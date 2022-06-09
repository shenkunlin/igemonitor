package com.itheima.ige.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.pojo.IncrementStatically;
import com.itheima.ige.monitor.pojo.ServerConfig;
import com.itheima.ige.wrap.IgeAttribute;

import java.util.List;

/**
 * <p>
 * 增量静态化配置 服务类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
public interface IncrementStaticallyService extends IService<IncrementStatically> {

    Result initiativeGenerate(IgeAttribute igeAttribute) throws Exception;

    ServerConfig getByUniqueId(String uniqueId);

    Result initiativeGenerateBatch(List<IgeAttribute> igeAttributes) throws Exception;

}
