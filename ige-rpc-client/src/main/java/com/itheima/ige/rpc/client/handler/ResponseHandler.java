package com.itheima.ige.rpc.client.handler;

import com.itheima.ige.rpc.client.loadbalancer.ServiceLoadBalancerFilter;
import com.itheima.ige.rpc.client.runner.RequestFuture;
import com.itheima.ige.rpc.client.runner.SyncRequestFuture;
import com.itheima.ige.rpc.data.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Rpc数据接收响应处理器
 */
@ChannelHandler.Sharable
public class ResponseHandler extends SimpleChannelInboundHandler<Response> {

    @Autowired
    private ServiceLoadBalancerFilter serviceLoadBalancerFilter;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
        // 获取请求回调信息
        RequestFuture requestFuture = SyncRequestFuture.syncRequest.get(response.getReqId());
        if(null != requestFuture) {
            // 设置回调结果
            requestFuture.setResponse(response);
        }else {
            throw new RuntimeException("An exception occurred to the service");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //获取handler
        Channel channel = ctx.channel();
        boolean active = channel.isActive();
        if(!active){
            //移除该handler
            serviceLoadBalancerFilter.removeHandler(channel);
        }
    }
}
