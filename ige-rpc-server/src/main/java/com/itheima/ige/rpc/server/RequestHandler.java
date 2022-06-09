package com.itheima.ige.rpc.server;

import com.itheima.ige.rpc.data.Request;
import com.itheima.ige.rpc.data.Response;
import com.itheima.ige.rpc.util.SpringContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@ChannelHandler.Sharable
public class RequestHandler extends SimpleChannelInboundHandler<Request> {

    /***
     * 会话注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //记录会话，用于主动向客户端发送请求
        ChannelMap.addChannel(ctx.channel().toString(),ctx.channel());
        super.channelRegistered(ctx);
    }

    /****
     * 接收请求并处理请求
     * @param ctx
     * @param request
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        //1、封装响应对象->RpcResponse
        Response response = new Response();

        //2、解析传过来的数据RpcRequest->ClassName->com.itheima.ige.rpc...->Method->parameter..
        String requestId = request.getReqId();
        String className = request.getClassName();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        try {
            //3、找到Class对应的方法执行反射调用
            Object targetClass = SpringContext.getBean(Class.forName(className));
            Method targetMethod = targetClass.getClass().getMethod(methodName, parameterTypes);
            Object result = targetMethod.invoke(targetClass, parameters);

            //4、封装响应对象->Response
            response.setReqId(requestId);
            response.setResult(result);
        } catch (Throwable e) {
            e.printStackTrace();
            response.setCause(e);
        }
        //5、响应结果
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        String key = ctx.channel().toString();
        int count = ChannelMap.removeChannelByName(key);
    }
}