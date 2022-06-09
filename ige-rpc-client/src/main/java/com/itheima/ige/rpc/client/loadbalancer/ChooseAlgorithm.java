package com.itheima.ige.rpc.client.loadbalancer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 负载均衡
 */
public class ChooseAlgorithm {

    //计数
    private static AtomicInteger countAto = new AtomicInteger(0);

    /***
     * 轮询
     */
    public static int roundRobin(int size){
        return countAto.getAndIncrement() % size;
    }

    /*******
     * 随机算法
     */
    public static int random(int size){
        return new Random().nextInt(size);
    }
}
