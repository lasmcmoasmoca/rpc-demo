package com.leec.rpcdemo.handler;

import com.alibaba.fastjson.JSON;
import com.leec.rpcdemo.bean.RpcRequest;
import com.leec.rpcdemo.bean.RpcResponse;
import com.leec.rpcdemo.filter.ChannelFilter;

import com.leec.rpcdemo.register.ServiceRegister;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * @author lichao
 */
@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.fireChannelRead(msg);
        System.out.println(msg.toString());
        RpcRequest request = JSON.parseObject(msg.toString(), RpcRequest.class);
        Object object = ServiceRegister.getService(request.getServiceName());
        Method  method = object.getClass().getDeclaredMethod(request.getMethodName(),request.getParameterTypes());
//        Method method = object.getClass().getMethod(request.getMethodName(),request.getParameterTypes());
        Object result = method.invoke(object,request.getArgs());
        RpcResponse response = new RpcResponse();
        response.setMsg("ok");
        response.setStatus(0);
        response.setResult(result);
        request.getRequestId();
        ctx.writeAndFlush(JSON.toJSONString(response)).addListener(future -> {
            System.out.println("server response success result:{}" + JSON.toJSONString(response));
        });
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        ChannelFilter channelFilter = new ChannelFilter();
        channelFilter.removeChannel(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ChannelFilter channelFilter = new ChannelFilter();
        channelFilter.removeChannel(ctx.channel());
        System.out.println("exceptionCaught "+cause.fillInStackTrace());
    }
}
