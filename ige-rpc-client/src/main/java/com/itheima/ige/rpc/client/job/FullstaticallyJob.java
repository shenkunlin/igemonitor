package com.itheima.ige.rpc.client.job;
import com.itheima.ige.monitor.rpc.service.StaticService;
import com.itheima.ige.rpc.client.job.fixed.IgeJob;
import com.itheima.ige.rpc.client.job.fixed.IgePage;
import com.itheima.ige.rpc.util.SpringContext;
import com.itheima.ige.util.IgeConstant;
import com.itheima.ige.wrap.IgeAttribute;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class FullstaticallyJob implements Job {


    /***
     * 定时任务
     * @param context
     * @throws JobExecutionException
     */
    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //任务数据
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String namespace = jobDataMap.get(IgeConstant.NAMESPACE).toString();
        String uniqueid = jobDataMap.get(IgeConstant.UNIQUEID).toString();

        Environment environment = SpringContext.getBean(Environment.class);
        String bean = environment.getProperty(IgeConstant.FULLJOB_PREFIX+namespace+"."+uniqueid+"."+IgeConstant.BEANNAME);

        //服务调用
        if(!ObjectUtils.isEmpty(bean)){
            IgeJob igeJob = SpringContext.getBean(bean);
            if(igeJob!=null){
                call(igeJob,namespace,uniqueid);
            }
        }
    }

    @Async
    public void call(IgeJob igeJob,String namespace,String uniqueid) throws Exception {
        //1.分页查询总数量
        long total = igeJob.total();
        int page=1;
        int totalPages = (int) (total%10==0? total/10 : (total/10)+1);

        //获取生成接口实力
        StaticService staticService = SpringContext.getBean(StaticService.class);

        //2.分页查询数据
        while (page<=totalPages){
            IgePage igePage = new IgePage();
            igePage.setPage(page);
            igePage.setNamespace(namespace);
            igePage.setUniqueId(uniqueid);
            List<IgeAttribute> list = igeJob.list(igePage);

            //3.调用远程服务
            String result = staticService.fullGenerateBatch(list);
            //4.将结果响应给指定方法
            igeJob.callback(result);
            page++;
        }
    }
}
