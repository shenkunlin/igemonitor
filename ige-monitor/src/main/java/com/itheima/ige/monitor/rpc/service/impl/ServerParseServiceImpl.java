package com.itheima.ige.monitor.rpc.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.ige.monitor.mapper.ServerConfigMapper;
import com.itheima.ige.monitor.pojo.ServerConfig;
import com.itheima.ige.util.IgeConstant;
import com.itheima.ige.wrap.ServerInfoConfig;
import com.itheima.storage.service.ServerParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ServerParseServiceImpl implements ServerParseService {

    @Autowired
    private ServerConfigMapper serverConfigMapper;

    /***
     * 查询所有服务
     * @return
     */
    @Override
    public List<ServerInfoConfig> allServer() {
        List<ServerConfig> serverConfigs = serverConfigMapper.selectList(Wrappers.<ServerConfig>lambdaQuery().eq(ServerConfig::getEnable, 1));
        return parse(serverConfigs);
    }

    /***
     * ServerConfig转换成ServerInfoConfig
     */
    public List<ServerInfoConfig> parse(List<ServerConfig> serverConfigs){
        List<ServerInfoConfig> configs = new ArrayList<ServerInfoConfig>();
        for (ServerConfig serverConfig : serverConfigs) {
            ServerInfoConfig serverInfoConfig = new ServerInfoConfig();
            serverInfoConfig.setId(serverConfig.getId());
            serverInfoConfig.setType(serverConfig.getServerType());
            serverInfoConfig.setConfig(JSON.parseObject(serverConfig.getConfigContent(), Map.class));

            //url解析
            if(serverInfoConfig.getType()== IgeConstant.OUTTYPE_FASTDFS){
                serverInfoConfig.setId(serverConfig.getId());
                serverInfoConfig.setUrl(serverInfoConfig.getConfig().get(IgeConstant.STORAGE_FASTDFS_SERVER_HTTP));
            }else if(serverInfoConfig.getType()== IgeConstant.OUTTYPE_ALIYUNOSS){
                serverInfoConfig.setUrl(serverInfoConfig.getConfig().get(IgeConstant.STORAGE_ALIYUNOSS_URL));
            }
            else if(serverInfoConfig.getType()== IgeConstant.OUTTYPE_MINIO){
                serverInfoConfig.setUrl(serverInfoConfig.getConfig().get(IgeConstant.STORAGE_ALIYUNOSS_URL_ENDPOINT));
            }
            if(!ObjectUtils.isEmpty(serverInfoConfig.getUrl())){
                configs.add(serverInfoConfig);
            }
        }
        return configs;
    }

}
