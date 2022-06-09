package com.itheima.ige.rpc.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInstance {

    //服务IP
    private String ip;

    //服务端口
    private int port;


}