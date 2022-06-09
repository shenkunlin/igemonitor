package com.itheima.ige.rpc.client.loadbalancer;

import com.itheima.ige.rpc.client.handler.ChannelHolder;
import com.itheima.ige.rpc.config.ClientConfig;
import io.netty.channel.Channel;
import javassist.NotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@Slf4j
@Data
public class ServiceLoadBalancerFilter {

    @Autowired
    private ClientConfig clientConfig;

    //可用服务列表
    private List<ChannelHolder> channels = new CopyOnWriteArrayList<ChannelHolder>();

    /***
     * 从服务列表中选择一个服务
     * @return
     */
    public ChannelHolder choose() throws Exception {
        if(channels.size()==0){
            throw new NotFoundException("No available service was found");
        }
        //轮询
        int index = 0;
        if(clientConfig.getLb().equals(ClientConfig.KEY_LB_ROUND)){
            //获取服务下标
            index = ChooseAlgorithm.roundRobin(channels.size());
        }else{
            //默认随机模式
            index = ChooseAlgorithm.random(channels.size());
        }
        //获取服务
        return channels.get(index);
    }

    public void addChannels(ChannelHolder channel) {
        this.channels.add(channel);
    }

    //移除已关闭会话
    public void removeHandler(Channel channel) {
        int size = this.channels.size();
        for (int i = 0; i <size ; i++) {
            //获取通道对象
            ChannelHolder holder = this.channels.get(i);
            //匹配会话
            if(holder.getChannel()==channel){
                this.channels.remove(holder);
                log.debug("无效会话清理，清理前有{}个，清理后剩余{}个",size,this.channels.size());
                return;
            }
        }
    }
}
