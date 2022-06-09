package com.itheima.ige.rpc.client.proxy;

import com.itheima.ige.rpc.client.handler.ClientInitializer;
import com.itheima.ige.rpc.client.handler.ResponseHandler;
import com.itheima.ige.rpc.client.job.DynamicJob;
import com.itheima.ige.rpc.client.job.IgeClient;
import com.itheima.ige.rpc.client.loadbalancer.ServiceLoadBalancerFilter;
import com.itheima.ige.rpc.client.loadbalancer.ServiceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanRegister {

    @Bean
    public ClientInitializer clientInitializer(){
        return new ClientInitializer();
    }

    @Bean
    public ResponseHandler responseHandler(){
        return new ResponseHandler();
    }

    @Bean
    public DynamicJob dynamicJob(){
        return new DynamicJob();
    }

    @Bean
    public IgeClient igeClient(){
        return new IgeClient();
    }

    @Bean
    public ServiceManager serviceManager(){
        return new ServiceManager();
    }

    @Bean
    public ServiceLoadBalancerFilter serviceLoadBalancerFilter(){
        return new ServiceLoadBalancerFilter();
    }
}
