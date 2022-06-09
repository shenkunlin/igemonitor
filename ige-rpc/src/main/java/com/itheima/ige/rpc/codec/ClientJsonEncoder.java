package com.itheima.ige.rpc.codec;

import com.alibaba.fastjson.JSON;
import com.itheima.ige.rpc.data.Request;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ClientJsonEncoder extends MessageToByteEncoder<Request> {

	  @Override
	  protected void encode(ChannelHandlerContext ctx, Request request, ByteBuf buf)
	      throws Exception {
		  if(null != request){
		    String json = JSON.toJSONString(request);
		    ctx.writeAndFlush(Unpooled.wrappedBuffer(json.getBytes()));
		  }
	  }
	}