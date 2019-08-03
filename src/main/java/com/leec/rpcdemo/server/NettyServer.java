package com.leec.rpcdemo.server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.leec.rpcdemo.pipeline.RpcServerChannelPipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author lichao
 */
@Component
public class NettyServer {

    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    @PostConstruct
    public void init(){
//        start();
    }

    public synchronized void start(){
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        doStart();
    }

    private void doStart(){
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true)
            .childOption(ChannelOption.TCP_NODELAY, true).childHandler(new RpcServerChannelPipeline());
        ChannelFuture channelFuture = serverBootstrap.bind(18898);
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("netty server start success.....");
            }
        });
        try {
            ChannelFuture future = channelFuture.sync();

            future.channel().closeFuture().addListener(future1 -> close());
        } catch (InterruptedException e) {
            //ignore
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destroy(){
        close();
    }

    public synchronized void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

    }
}
