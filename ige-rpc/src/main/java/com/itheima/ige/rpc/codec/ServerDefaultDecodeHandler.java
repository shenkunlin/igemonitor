package com.itheima.ige.rpc.codec;

import com.itheima.ige.rpc.data.Request;
import com.itheima.ige.rpc.util.ProtoSerializerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 客户端解码器
 */
public class ServerDefaultDecodeHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        // 长度不够， 直接退出
        if (in.readableBytes() <= 4) {
            return;
        }

        int length = in.readInt();
        in.markReaderIndex();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
        } else {
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            //String str = new String(bytes,"UTF-8");
            // 通过protostuff封装组件， 实现反序列化操作
            Request request = ProtoSerializerUtil.deserialize(bytes, Request.class);
            //Request request = JSONSerializerUtil.deserialize(bytes, Request.class);
            list.add(request);
        }
    }
}
