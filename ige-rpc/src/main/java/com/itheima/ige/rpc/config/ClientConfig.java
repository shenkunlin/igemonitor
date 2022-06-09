package com.itheima.ige.rpc.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class ClientConfig {

    public static final String KEY_SCANS="ige.client.scans";
    public static final String KEY_RPC_CLIENT="com.itheima.ige.monitor.rpc.service";
    public static final String KEY_NODES="ige.rpc.nodes";
    public static final String KEY_LB="ige.rpc.lb";

    //负载均衡算法
    public static final String KEY_LB_RANDOM="random";
    public static final String KEY_LB_ROUND="round";

    @Value("${ige.client.scans:null}")
    private String scans;

    @Value("${ige.client.delay:true}")
    private boolean delay;

    @Value("${ige.rpc.nodes:null}")
    private List<String> nodes;

    @Value("${ige.rpc.lb:random}")
    private String lb;


    @Value("${ige.client.pool:30}")
    private int pool;

    //应用名字
    @Value("${ige.rpc.appname:shop}")
    private String appname;
}
