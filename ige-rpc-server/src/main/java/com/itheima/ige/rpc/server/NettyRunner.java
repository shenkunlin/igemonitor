package com.itheima.ige.rpc.server;

import com.itheima.ige.rpc.config.ServerConfig;
import com.itheima.ige.rpc.util.SpringContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/****
 * 基于Netty的处理服务
 */
@Slf4j
public class NettyRunner implements Runnable {

    //主线程池
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ServerConfig igeConfig;
    private ServerInitializer serverInitializer;

    public NettyRunner() {
        this.igeConfig = SpringContext.getBean(ServerConfig.class);
        this.serverInitializer = SpringContext.getBean(ServerInitializer.class);        //初始化线
        //初始化线
        this.boss = new NioEventLoopGroup(igeConfig.getBoss());
        this.worker =new NioEventLoopGroup(igeConfig.getWorker());
    }

    @Override
    public void run() {
        // 1. Netty服务配置
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                // 设置非阻塞,用它来建立新accept的连接,用于构造ServerSocketChannel的工厂类
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                // 存放请求的队列的最大长度
                .option(ChannelOption.SO_BACKLOG, igeConfig.getBacklog())
                // 长链接
                .childOption(ChannelOption.SO_KEEPALIVE, igeConfig.isKeepalive())
                // 核心业务处理流程
                .childHandler(serverInitializer);
        try {
            // 2. 绑定端口， 启动服务
            ChannelFuture future = bootstrap.bind(igeConfig.getPort()).sync();
            if(future.isSuccess()){
                log.info("RPC启动，端口是{}",igeConfig.getPort());
            }
            // 3. 服务同步阻塞方式运行
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully().syncUninterruptibly();
            worker.shutdownGracefully().syncUninterruptibly();
        }
    }
}