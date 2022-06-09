package com.itheima.ige.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.framework.util.ResultCode;
import com.itheima.ige.monitor.mapper.NamespaceConfigMapper;
import com.itheima.ige.monitor.pojo.NamespaceConfig;
import com.itheima.ige.monitor.service.NamespaceConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 命名空间配置 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Service
public class NamespaceConfigServiceImpl extends ServiceImpl<NamespaceConfigMapper, NamespaceConfig> implements NamespaceConfigService {

    /**
     * 添加命名空间
     * @param record
     * @return
     */
    @Override
    public Result<NamespaceConfig> addNamespace(NamespaceConfig record) {
        //判断是否已经存在
        long count = super.count(new LambdaQueryWrapper<NamespaceConfig>()
                .eq(NamespaceConfig::getNamespaceName, record.getNamespaceName()));
        if(count>0){
            return Result.error(ResultCode.EXIST);
        }
        //保存
        super.save(record);
        return Result.ok(record);
    }
}
