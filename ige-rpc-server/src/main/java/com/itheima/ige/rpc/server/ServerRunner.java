package com.itheima.ige.rpc.server;
import com.itheima.ige.rpc.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ServerRunner implements ApplicationRunner {

    @Autowired
    private ServerConfig igeConfig;

    //线程池
    private static ExecutorService executor;

    /***
     * 初始化RPC服务
     */
    public void start(){
        executor = Executors.newFixedThreadPool(igeConfig.getStart());
        executor.execute(new NettyRunner());
        //服务注册
        log.debug("服务配置信息:{}",igeConfig.toString());
    }

    /***
     * 资源回收
     */
    @PreDestroy
    public void destroy(){
        if (executor != null) {
            executor.shutdown();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }
}