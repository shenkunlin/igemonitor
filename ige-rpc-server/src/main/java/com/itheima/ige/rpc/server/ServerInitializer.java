package com.itheima.ige.rpc.server;

import com.itheima.ige.rpc.codec.ServerDefaultDecodeHandler;
import com.itheima.ige.rpc.codec.ServerDefaultEncodeHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/***
 * 服务端Netty连接初始化配置
 */
@Component
@ChannelHandler.Sharable
public class ServerInitializer extends ChannelInitializer<Channel> {

    @Resource
    private RequestHandler requestHandler;

    public static final AttributeKey<String> igeNameKey = AttributeKey.valueOf("igeClientName");

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(1024*20, 0, 2, 0, 2))
                .addLast(new LengthFieldPrepender(2))
                //.addLast(new ServerJsonDecoder())
                //.addLast(new ServerJsonEncoder())
                // 解码器
                .addLast(new ServerDefaultDecodeHandler())
                // 编码器
                .addLast(new ServerDefaultEncodeHandler())
                // 业务处理器
                .addLast(requestHandler);
    }
}
