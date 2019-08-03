package com.leec.rpcdemo.pipeline;

import com.leec.rpcdemo.handler.NettyServerHandler;
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
public class RpcServerChannelPipeline extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline channelPipeline = channel.pipeline();
        channelPipeline.addLast("encoder",new StringEncoder());
        channelPipeline.addLast("decoder",new StringDecoder());
//        channelPipeline.addLast("heartBeatChecker", new IdleStateHandler(0, 0, 30, MILLISECONDS));
        channelPipeline.addLast(new NettyServerHandler());
    }
}
