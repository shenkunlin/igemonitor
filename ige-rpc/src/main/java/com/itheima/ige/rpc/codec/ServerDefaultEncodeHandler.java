package com.itheima.ige.rpc.codec;

import com.itheima.ige.rpc.data.Response;
import com.itheima.ige.rpc.util.ProtoSerializerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Rpc客户端编码器
 */
public class ServerDefaultEncodeHandler extends MessageToByteEncoder<Response> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Response response, ByteBuf in) throws Exception {
        // 调用封装的protostuff公用组件， 实现序列化
        byte[] bytes = ProtoSerializerUtil.serialize(response);
        // byte[] bytes = JSONSerializerUtil.serialize(response);
        in.writeInt(bytes.length);
        in.writeBytes(bytes);
    }
}
