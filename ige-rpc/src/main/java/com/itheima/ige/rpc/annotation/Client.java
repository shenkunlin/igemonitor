package com.itheima.ige.rpc.annotation;

public @interface Client {
    //版本
    String version() default "";
}