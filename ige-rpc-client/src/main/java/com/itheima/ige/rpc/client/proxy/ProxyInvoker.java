package com.itheima.ige.rpc.client.proxy;
import com.itheima.ige.rpc.client.handler.ChannelHolder;
import com.itheima.ige.rpc.client.loadbalancer.ServiceManager;
import com.itheima.ige.rpc.client.runner.RequestFuture;
import com.itheima.ige.rpc.client.runner.SyncRequestFuture;
import com.itheima.ige.rpc.config.ClientConfig;
import com.itheima.ige.rpc.data.Request;
import com.itheima.ige.rpc.data.Response;
import com.itheima.ige.rpc.util.SpringContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.util.concurrent.TimeUnit;
public class ProxyInvoker {

    private static ClientConfig igeConfig;

    private static ServiceManager serviceManager;

    /**
     * 发送客户端请求
     *
     * @param rpcRequest
     * @throws InterruptedException
     */
    public static Response sendRequest(Request rpcRequest) throws Exception {
        if (serviceManager == null || igeConfig == null) {
            serviceManager = SpringContext.getBean(ServiceManager.class);
            igeConfig = SpringContext.getBean(ClientConfig.class);
        }
        //获取一个服务
        ChannelHolder channelHolder = serviceManager.choose();

        final RequestFuture<Response> responseFuture = new SyncRequestFuture(rpcRequest.getReqId());

        // 4. 将请求回调放置缓存
        SyncRequestFuture.syncRequest.put(rpcRequest.getReqId(), responseFuture);

        ChannelFuture channelFuture = channelHolder.getChannel().writeAndFlush(rpcRequest);

        // 6. 建立回调监听
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                // 7. 设置是否成功的标记
                responseFuture.setWriteResult(future.isSuccess());
                if (!future.isSuccess()) {
                    // 调用失败，清除连接缓存
                    SyncRequestFuture.syncRequest.remove(responseFuture.reqId());
                }
            }
        });

        // 8. 阻塞等待3秒
        Response result = responseFuture.get(500, TimeUnit.SECONDS);
        // 9. 移除连接缓存
        SyncRequestFuture.syncRequest.remove(rpcRequest.getReqId());
        return result;
    }
}