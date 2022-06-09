package com.itheima.ige.rpc.codec;
import com.alibaba.fastjson.JSON;
import com.itheima.ige.rpc.data.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class ClientJsonDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out)
            throws Exception {
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        Response response = JSON.parseObject(new String(bytes, CharsetUtil.UTF_8), Response.class);
        if (null != response) {
            out.add(response);
        }
    }
}