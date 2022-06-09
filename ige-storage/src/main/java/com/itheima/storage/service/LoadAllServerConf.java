package com.itheima.storage.service;

import com.alibaba.fastjson.JSON;
import com.itheima.ige.wrap.ServerInfoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoadAllServerConf {

    @Autowired(required = false)
    private ServerParseService serverParseService;

    //服务列表  key=url  value=服务信息
    private static ConcurrentHashMap<String,ServerInfoConfig> serverMap = new ConcurrentHashMap<String,ServerInfoConfig>();

    /***
     * 加载所有配置
     */
    @Scheduled(fixedRate = 10000)
    public void loadAll(){
        if(serverParseService!=null){
            //1.加载服务所有可用服务
            List<ServerInfoConfig> serverInfoConfigs = serverParseService.allServer();
            //2.循环将数据加入到serverMap中
            for (ServerInfoConfig serverInfoConfig : serverInfoConfigs) {
                ServerInfoConfig config = serverMap.putIfAbsent(serverInfoConfig.getUrl(), serverInfoConfig);
                if(config!=null){
                    synchronized (serverMap){
                        if(!JSON.toJSONString(config).equals(JSON.toJSONString(serverInfoConfig))){
                            serverMap.remove(serverInfoConfig.getUrl());
                            serverMap.put(serverInfoConfig.getUrl(), serverInfoConfig);
                        }
                    }
                }
            }
        }
    }

    /***
     * url匹配服务
     */
    public ServerInfoConfig matchServer(String url){
        if(serverMap==null || serverMap.size()==0){
            this.loadAll();
        }
        if(serverMap!=null){
            for (Map.Entry<String, ServerInfoConfig> entry : serverMap.entrySet()) {
                if(url.startsWith(entry.getKey())){
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}
