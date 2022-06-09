package com.itheima.ige.rpc.client.handler;

import com.itheima.ige.rpc.codec.ClientDefaultDecodeHandler;
import com.itheima.ige.rpc.codec.ClientDefaultEncodeHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@ChannelHandler.Sharable
public class ClientInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private ResponseHandler responseHandler;

    @Autowired
    private Environment environment;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                //.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2))
                //.addLast(new LengthFieldPrepender(2))
                .addLast(new LengthFieldBasedFrameDecoder(1024*20, 0, 2, 0, 2))
                .addLast(new LengthFieldPrepender(2))
                //.addLast(new ClientJsonEncoder())
                //.addLast(new ClientJsonDecoder())
                .addLast(new ClientDefaultEncodeHandler())
                .addLast(new ClientDefaultDecodeHandler())
                .addLast(responseHandler);
    }
}