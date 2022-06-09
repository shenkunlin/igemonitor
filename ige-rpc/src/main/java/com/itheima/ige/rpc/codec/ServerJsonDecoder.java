package com.itheima.ige.rpc.codec;
import com.alibaba.fastjson.JSON;
import com.itheima.ige.rpc.data.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class ServerJsonDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out)
            throws Exception {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        Request request = JSON.parseObject(new String(bytes, CharsetUtil.UTF_8), Request.class);
        if (null != request) {
            out.add(request);
        }
    }
}