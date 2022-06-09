package com.itheima.ige.monitor.job;

import com.itheima.ige.monitor.pojo.FullStatically;
import com.itheima.ige.monitor.service.FullStaticallyService;
import com.itheima.ige.rpc.util.SpringContext;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author：
 * @Description： https://nacos.io/zh-cn/docs/open-api.html
 ***/
public class FullStaticJob implements Job {

    /**
     * 全量任务
     * @param context
     * @throws JobExecutionException
     */
    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //任务数据
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        //查询任务信息
        FullStaticallyService fullStaticallyService = SpringContext.getBean(FullStaticallyService.class);
        FullStatically fullStatically = fullStaticallyService.getById(jobDataMap.get("id").toString());

        if(fullStatically!=null){
            //执行任务
            jobExecute(fullStatically);
        }
    }

    /**
     * 任务操作
     */
    public void jobExecute(FullStatically fullStatically) throws Exception {

    }


}
