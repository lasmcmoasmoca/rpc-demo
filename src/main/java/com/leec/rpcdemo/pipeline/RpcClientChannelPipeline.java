package com.leec.rpcdemo.pipeline;

import com.leec.rpcdemo.client.NettyClientHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author lichao
 */
public class RpcClientChannelPipeline extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline channelPipeline = channel.pipeline();
        //todo 实现自己编解码器
        channelPipeline.addLast("encoder",new StringEncoder());
        channelPipeline.addLast("decoder",new StringDecoder());
        channelPipeline.addLast("heartBeatChecker", new IdleStateHandler(0, 0, 30, MILLISECONDS));
        channelPipeline.addLast(new NettyClientHandler());
    }
}
