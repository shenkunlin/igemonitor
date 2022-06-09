package com.itheima.ige.rpc.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ServerConfig {

    /**
     * RPC通讯端口
     */
    @Value("${ige.rpc.port:28888}")
    private int port;

    @Value("${ige.thread.boss:5}")
    private int boss;

    @Value("${ige.thread.worker:20}")
    private int worker;

    @Value("${ige.thread.start:5}")
    private int start;

    @Value("${ige.so.backlog:1024}")
    private int backlog;


    @Value("${ige.so.keepalive:true}")
    private boolean keepalive;

    /**
     * Spring Boot 服务端口
     */
    @Value("${server.port}")
    private int serverPort;

}
