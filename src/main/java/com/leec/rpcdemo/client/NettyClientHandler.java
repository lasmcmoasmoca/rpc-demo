package com.leec.rpcdemo.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lichao
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private volatile Channel channel ;

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
        //todo response
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx,cause);
        ctx.close();
        System.out.println(cause.fillInStackTrace());
    }

}
