package com.itheima.ige.rpc.codec;

import com.itheima.ige.rpc.data.Request;
import com.itheima.ige.rpc.util.ProtoSerializerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Rpc客户端编码器
 */
public class ClientDefaultEncodeHandler extends MessageToByteEncoder<Request> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Request request, ByteBuf in) throws Exception {
        // 调用封装的protostuff公用组件， 实现序列化
        byte[] bytes = ProtoSerializerUtil.serialize(request);
        //byte[] bytes = JSONSerializerUtil.serialize(request);
        in.writeInt(bytes.length);
        in.writeBytes(bytes);
    }
}
