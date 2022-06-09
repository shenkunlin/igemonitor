package com.itheima.ige.rpc.client.loadbalancer;

import com.itheima.ige.rpc.client.handler.ChannelHolder;
import com.itheima.ige.rpc.client.handler.ClientInitializer;
import com.itheima.ige.rpc.config.ClientConfig;
import com.itheima.ige.rpc.data.ServiceInstance;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * 服务管理
 */
@Slf4j
public class ServiceManager {

    @Autowired
    private ServiceLoadBalancerFilter serviceLoadBalancerFilter;

    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private ClientInitializer rpcClientInitializer;

    //服务列表
    private List<ServiceInstance> services = new CopyOnWriteArrayList<ServiceInstance>();

    /***
     * 选择一个服务
     */
    public ChannelHolder choose() throws Exception {
        if(services.size()==0 && serviceLoadBalancerFilter.getChannels().size()==0){
            //初始化
            initCollections();
        }
        return serviceLoadBalancerFilter.choose();
    }

    /***
     * 初始化
     * @throws InterruptedException
     */
    @PostConstruct
    public void immediatelyInit() throws InterruptedException {
        if(!clientConfig.isDelay()){
            initCollections();
        }
    }

    /***
     * 创建所有服务链接
     * validServices
     */
    public void initCollections() throws InterruptedException {
        //解析所有服务地址
        parseServices();

        //创建所有服务链接
        if(services.size()>0){
            for (ServiceInstance service : services) {
                connect(service);
            }
        }
    }

    /***
     * 创建服务链接
     */
    public void connect(ServiceInstance serviceInstance) throws InterruptedException {
        //简单池化
        for (int i = 0; i < clientConfig.getPool(); i++) {
            //2)发起远程请求
            EventLoopGroup worker = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(serviceInstance.getIp(), serviceInstance.getPort())
                    .handler(rpcClientInitializer);

            //建立链接
            ChannelFuture future = bootstrap.connect().sync();
            if (future.isSuccess()) {
                ChannelHolder channelHolder = ChannelHolder.builder()
                        .channel(future.channel())
                        .eventLoopGroup(worker)
                        .build();
                //保存现有服务
                serviceLoadBalancerFilter.addChannels(channelHolder);
            }
        }
    }

    /***
     * 解析服务地址
     */
    public void parseServices(){
        //获取服务列表
        List<String> nodes = clientConfig.getNodes();
        //将服务列表转换成对象集合
        List<ServiceInstance> instances = new CopyOnWriteArrayList<ServiceInstance>();
        for (String node : nodes) {
            ServiceInstance instance = new ServiceInstance();
            String[] arr = node.split(":");
            instance.setIp(arr[0]);
            instance.setPort(Integer.valueOf(arr[1]));
            instances.add(instance);
        }
        services=instances;
    }

    /***
     * 定时检查上线服务
     */
    @Scheduled(fixedRate=10000,initialDelay = 1000)
    public void loadOnlineService() {
        //获取当前服务数据
        List<ChannelHolder> channels = serviceLoadBalancerFilter.getChannels();

        if(services.size()==0){
            //初始化服务配置
            parseServices();
            channels = serviceLoadBalancerFilter.getChannels();
        }

        if(services.size()>0){
            //获取所有服务
            for (ServiceInstance service : services) {
                boolean exist = false;
                for (ChannelHolder holder : channels) {
                    Channel channel = holder.getChannel();
                    InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
                    String ip = socketAddress.getAddress().getHostAddress();
                    int port = socketAddress.getPort();
                    //服务已检测到
                    if(ip.equals(service.getIp()) && port==service.getPort()){
                        exist=true;
                        break;
                    }
                }
                //已下线服务尝试链接
                if(!exist){
                    try {
                        connect(service);
                        log.debug("检测到服务{}已上线",service);
                    } catch (Exception e) {
                        log.debug("检测到服务{}已下线",service);
                    }
                }
            }
        }
    }

    public List<ServiceInstance> getServices() {
        return services;
    }
}
