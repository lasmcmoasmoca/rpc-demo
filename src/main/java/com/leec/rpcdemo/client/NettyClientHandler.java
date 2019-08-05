package com.leec.rpcdemo.client;

import com.alibaba.fastjson.JSON;
import com.leec.rpcdemo.bean.MessageFuture;
import com.leec.rpcdemo.bean.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lichao
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private volatile Channel channel ;

    private static volatile ConcurrentHashMap<String, MessageFuture> map = new ConcurrentHashMap();
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        String address = this.channel.remoteAddress().toString();
        System.out.println(address);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object response) throws Exception {
        System.out.println(response.toString());
        RpcResponse rpcResponse = JSON.parseObject(response.toString(), RpcResponse.class);
        MessageFuture future = map.get(rpcResponse.getRequestId());
        future.call(rpcResponse);
        map.remove(rpcResponse.getRequestId());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx,cause);
        ctx.close();
        System.out.println(cause.fillInStackTrace());
    }

    public static void addCallBack(String requestId, MessageFuture messageFuture) {
        map.put(requestId, messageFuture);
    }



}
