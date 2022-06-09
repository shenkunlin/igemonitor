package com.itheima.ige.rpc.client.job;

import com.alibaba.fastjson.JSON;
import com.itheima.ige.framework.util.Result;
import com.itheima.ige.monitor.rpc.pojo.FullStatically;
import com.itheima.ige.monitor.rpc.service.StaticService;
import com.itheima.ige.rpc.client.loadbalancer.ServiceManager;
import com.itheima.ige.rpc.config.ClientConfig;
import com.itheima.ige.rpc.data.ServiceInstance;
import com.itheima.ige.util.IgeConstant;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class IgeClient {

    @Autowired
    private StaticService staticService;

    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private DynamicJob dynamicJob;

    //所有任务
    private ConcurrentHashMap<String, FullStatically> concurrentHashMap = new ConcurrentHashMap<String,FullStatically>();

    /***
     * 初始化加载所有全量任务
     */
    @Scheduled(fixedRate = 10000)
    public void loadAllFullStaticJobs() throws SchedulerException {
        //获取服务列表
        List<ServiceInstance> services = serviceManager.getServices();
        if(staticService==null || services==null || services.size()==0){
            return;
        }
        String result = staticService.loadAllFullJobs(clientConfig.getAppname());
        if(!ObjectUtils.isEmpty(result)){
            //将数据转换成List
            Result<List> resultData = JSON.parseObject(result,Result.class);
            if(resultData.getData()!=null && resultData.getData().size()>0){
                List list = resultData.getData();
                for (Object o : list) {
                    //任务存入到ConcurrentHashMap中
                    FullStatically fullStatically = JSON.parseObject(JSON.toJSONString(o),FullStatically.class);

                    //如果任务存在，则匹配是否变更
                    String key = fullStatically.getNamespaceName()+"|"+fullStatically.getUniqueId();
                    FullStatically fullResult = concurrentHashMap.putIfAbsent(key, fullStatically);
                    if(fullResult!=null){
                        synchronized (fullResult){
                            if(JSON.toJSONString(fullResult).equals(JSON.toJSONString(fullStatically))){
                                continue;
                            }
                        }
                    }

                    //动态创建定时任务
                    dynamicJob.create(
                            fullStatically.getJobName(),
                            fullStatically.getNamespaceName(),
                            fullStatically.getCron(),
                            FullstaticallyJob.class,
                            IgeConstant.NAMESPACE+"="+fullStatically.getNamespaceName()+";"+
                                    IgeConstant.UNIQUEID+"="+fullStatically.getUniqueId());
                }
            }
        }
    }
}
