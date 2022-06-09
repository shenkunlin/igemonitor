package com.itheima.ige.rpc.codec;

import com.alibaba.fastjson.JSON;
import com.itheima.ige.rpc.data.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerJsonEncoder extends MessageToByteEncoder<Response> {

	  @Override
	  protected void encode(ChannelHandlerContext ctx, Response response, ByteBuf buf)
	      throws Exception {
		  if(null != response){
		    String json = JSON.toJSONString(response);
		    ctx.writeAndFlush(Unpooled.wrappedBuffer(json.getBytes()));
		  }
	  }
	}