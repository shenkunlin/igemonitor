package com.itheima.ige.monitor.service.impl;
import com.itheima.ige.monitor.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String demo(String username){
        return "rpc message "+username;
    }
}
