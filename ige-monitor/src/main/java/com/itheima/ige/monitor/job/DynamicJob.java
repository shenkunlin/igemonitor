package com.itheima.ige.monitor.job;

import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：
 * @Description：动态创建定时任务
 ***/
@Component
public class DynamicJob {

    @Autowired
    private Scheduler scheduler;

    /**
     * 创建定时任务
     */
    public void create(String name, String group, String cron, Class clazz) throws SchedulerException {
        create(name,group,cron,clazz,null);
    }

    /**
     * 创建定时任务
     */
    public void create(String name, String group, String cron, Class clazz, String parameters) throws SchedulerException {
        //任务信息封装
        JobKey jobKey = new JobKey(name,group);
        //判断任务是否已经存在，如果存在这个任务，则删除
        if(scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
        // 任务创建
        JobDetail job = job(jobKey,clazz,parameters);

        //触发器构建
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(name,group)
                .withSchedule(cronScheduleBuilder).build();
        //加入动态定时任务
        scheduler.scheduleJob(job,trigger);
    }

    /**
     * 暂停定时任务
     */
    public void stop(String name,String group) throws SchedulerException {
        JobKey key = JobKey.jobKey(name, group);
        JobDetail job = scheduler.getJobDetail(key);
        if (job == null) {
            return;
        }
        scheduler.pauseJob(key);
    }

    /**
     * 恢复定时任务
     */
    public void resume(String name,String group) throws SchedulerException {
        JobKey key = JobKey.jobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(key);
        if (jobDetail == null) {
            return;
        }
        scheduler.resumeJob(key);
    }

    /**
     * 删除定时任务
     */
    public void remove(String name,String group) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
        JobKey jobKey = JobKey.jobKey(name, group);
        Trigger trigger =  scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return;
        }
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(jobKey);
    }

    /**
     * 创建任务
     */
    public JobDetail job(JobKey jobKey,Class clazz,String parameters){
        JobBuilder jobBuilder = JobBuilder.newJob(clazz)
                // 任务标识，及任务分组
                .withIdentity(jobKey);
        //参数
        if(!StringUtils.isEmpty(parameters)){
            //参数结构  name1=value1
            String[] parameterArray = parameters.split(";");
            for (String str : parameterArray) {
                String[] parameter = str.split("=");
                jobBuilder.usingJobData(parameter[0],parameter[1]);
            }
        }
        return jobBuilder.build();
    }
}