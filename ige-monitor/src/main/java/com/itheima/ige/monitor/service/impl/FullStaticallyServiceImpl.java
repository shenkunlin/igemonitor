package com.itheima.ige.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.job.DynamicJob;
import com.itheima.ige.monitor.job.FullStaticJob;
import com.itheima.ige.monitor.mapper.FullStaticallyMapper;
import com.itheima.ige.monitor.mapper.NamespaceConfigMapper;
import com.itheima.ige.monitor.pojo.FullStatically;
import com.itheima.ige.monitor.pojo.NamespaceConfig;
import com.itheima.ige.monitor.pojo.ServerConfig;
import com.itheima.ige.monitor.service.FullStaticallyService;
import com.itheima.ige.template.TemplateProcessor;
import com.itheima.ige.util.IgeConstant;
import com.itheima.ige.wrap.IgeAttribute;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 全量静态化配置 服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-03-14
 */
@Service
public class FullStaticallyServiceImpl extends ServiceImpl<FullStaticallyMapper, FullStatically> implements FullStaticallyService {

    @Autowired
    private FullStaticallyMapper fullStaticallyMapper;

    @Autowired
    private DynamicJob dynamicJob;

    @Autowired
    @Qualifier("freemarkerProcessor")
    private TemplateProcessor templateProcessor;

    @Autowired
    private NamespaceConfigMapper namespaceConfigMapper;

    /***
     * 删除全量定时任务
     * @param id
     * @return
     */
    @Override
    public Result removeJob(Serializable id) throws SchedulerException {
        //查询任务
        FullStatically fullStatically = fullStaticallyMapper.selectById(id);

        if(fullStatically!=null){
            int count = fullStaticallyMapper.deleteById(id);

            if(count>0){
                //删除任务
                dynamicJob.remove(fullStatically.getJobName(),fullStatically.getNamespaceName());
                return Result.ok();
            }
        }
        return Result.error("删除任务失败");
    }

    /***
     * 添加全量任务
     * @param record
     * @return
     */
    @Override
    public boolean saveJob(FullStatically record) throws SchedulerException {
        NamespaceConfig namespace = namespaceConfigMapper.selectById(record.getNamespaceId());
        record.setNamespaceName(namespace.getNamespaceName());
        //添加任务
        if(fullStaticallyMapper.insert(record)>0){
            return true;
        }
        return false;
    }

    /**
     * 开启或停用任务
     * @return
     */
    @Override
    public Result enable(String id,Integer status) throws SchedulerException {
        FullStatically fullStatically = fullStaticallyMapper.selectById(id);
        fullStatically.setStatus(status);
        int count = fullStaticallyMapper.updateById(fullStatically);
        if(count>0){
            switch (status){
                case IgeConstant.STATUS_TE_1:
                    //启用
                    dynamicJob.create(fullStatically.getJobName(),fullStatically.getNamespaceName(),fullStatically.getCron(),FullStaticJob.class,"id="+id);
                    return Result.ok();
                case IgeConstant.STATUS_UTE_2:
                    //禁用，删除任务
                    dynamicJob.remove(fullStatically.getJobName(),fullStatically.getNamespaceName());
                    return Result.ok();
            }
        }
        return Result.error("任务状态操作异常！");
    }

    /**
     * 任务修改
     * @param record
     * @return
     */
    @Override
    public Result modifyJob(FullStatically record) throws SchedulerException {
        NamespaceConfig namespace = namespaceConfigMapper.selectById(record.getNamespaceId());
        record.setNamespaceName(namespace.getNamespaceName());
        //原任务
        FullStatically oldJob = fullStaticallyMapper.selectById(record.getId());
        //修改
        int count = fullStaticallyMapper.updateById(record);
        if(count>0 && oldJob.getStatus()==IgeConstant.STATUS_TE_1){
            //1)删除原来任务
            dynamicJob.remove(oldJob.getJobName(),oldJob.getNamespaceName());
            //2)执行新任务
            dynamicJob.create(record.getJobName(),record.getNamespaceName(),record.getCron(),FullStaticJob.class,"id="+record.getId());
        }
        return Result.ok();
    }

    /**
     * 全量化静态页生成
     * @param data
     * @param fullStatically
     */
    @Override
    public void generate(List data, FullStatically fullStatically, ServerConfig serverConfig) throws Exception {
        //1)本地存储模式
        if(fullStatically.getOutType()==IgeConstant.FULLSTATICALLY_OUTTYPE_LOCAL){
            for (Object datum : data) {
                localGen(fullStatically,JSON.parseObject(JSON.toJSONString(datum),IgeAttribute.class));
            }
        }
    }

    /**
     * 本地生成
     * @param fullStatically
     * @param igeAttribute
     * @throws Exception
     */
    private void localGen(FullStatically fullStatically, IgeAttribute igeAttribute) throws Exception {
        templateProcessor.generateDir(
                fullStatically.getTemplate(),   //模板
                igeAttribute.getData(), //数据模型
                igeAttribute.getFileName(),             //文件名字
                fullStatically.getLocalPath());    //存储路径
    }
}
