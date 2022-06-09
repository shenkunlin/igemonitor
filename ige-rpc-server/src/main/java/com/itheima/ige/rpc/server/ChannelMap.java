package com.itheima.ige.rpc.server;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelMap {

    public static int channelNum=0;
    private static ConcurrentHashMap<String,Channel> channelHashMap=new ConcurrentHashMap<String,Channel>();//concurrentHashmap以解决多线程冲突

    public static ConcurrentHashMap<String, Channel> getChannelHashMap() {
        return channelHashMap;
    }

    public static Channel getChannelByName(String name){
        return channelHashMap.get(name);
    }
    public static void addChannel(String name, Channel channel){
        channelHashMap.put(name,channel);
        channelNum++;
    }
    public static int removeChannelByName(String name){
        if(channelHashMap.containsKey(name)){
            channelHashMap.remove(name);
            return 0;
        }else{
            return 1;
        }
    }

}
