package com.itheima.ige.monitor.job;

import com.itheima.ige.rpc.data.Response;
import com.itheima.ige.rpc.server.ChannelMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JobTest {

    public static final AttributeKey<String> igeNameKey = AttributeKey.valueOf("clientName");

    /***
     * 测试任务
     */
    //@Scheduled(cron = "0/50 * * * * ?")
    public void test(){
        //1.获取所有会话
        ConcurrentHashMap<String, Channel> channelHashMap = ChannelMap.getChannelHashMap();

        //2.主动向客户端发送消息
        if(channelHashMap.size()>0){
            for (Map.Entry<String, Channel> entry : channelHashMap.entrySet()) {
                Channel channel = entry.getValue();
                Attribute<String> attr1 = channel.attr(igeNameKey);
                System.out.println(channel.hasAttr(igeNameKey));
                if(attr1==null){
                    attr1.setIfAbsent("hahhahahaha");
                }

                String attr = channel.attr(igeNameKey).get();
                System.out.println("attr---->"+attr);


                Response response = new Response();
                response.setResult("hello!");
                channel.writeAndFlush(response).addListener((ChannelFutureListener) future -> {
                    StringBuilder sb = new StringBuilder();
                    if (future.isSuccess()) {
                        System.out.println(sb.toString()+"回写成功");
                    } else {
                        System.out.println(sb.toString()+"回写失败");
                    }
                });
            }
        }
    }
}
