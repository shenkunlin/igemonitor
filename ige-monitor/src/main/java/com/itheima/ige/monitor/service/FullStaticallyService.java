package com.itheima.ige.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.pojo.FullStatically;
import com.itheima.ige.monitor.pojo.ServerConfig;
import org.quartz.SchedulerException;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 全量静态化配置 服务类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
public interface FullStaticallyService extends IService<FullStatically> {
    Result removeJob(Serializable id) throws SchedulerException;

    boolean saveJob(FullStatically record) throws SchedulerException;

    Result enable(String id,Integer status) throws SchedulerException;

    Result modifyJob(FullStatically record) throws SchedulerException;

    void generate(List data, FullStatically fullStatically,ServerConfig serverConfig) throws Exception;
}
