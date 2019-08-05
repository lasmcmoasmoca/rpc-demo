package com.leec.rpcdemo.client;

import com.leec.rpcdemo.bean.RpcRequest;
import com.leec.rpcdemo.pipeline.RpcClientChannelPipeline;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lichao
 */
public class NettyClient {

    private String host;

    private int port;

    private Bootstrap bootstrap;

    private EventLoopGroup eventExecutors;

    private volatile static NettyClient nettyClient;

    private volatile Channel channel;
    private NettyClient(String host,int port){
        this.host = host;
        this.port = port;
        start();
    }


    public static NettyClient getInstance(){
        if(nettyClient ==null) {
            synchronized (NettyClient.class) {
                if (nettyClient == null) {
                    nettyClient = new NettyClient("127.0.0.1", 18898);
                }
            }
        }
        return nettyClient;
    }



    public synchronized void start(){
        bootstrap = new Bootstrap();
        eventExecutors = new NioEventLoopGroup();
        doStart();
    }

    private void doStart(){
        bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_KEEPALIVE,true)
            .handler(new RpcClientChannelPipeline());
        ChannelFuture channelFuture = bootstrap.connect(host,port);
        try {
            channelFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("client connect success");
            }
        });
        channel =  channelFuture.channel();
        channel.closeFuture().addListener(future -> close());

    }


    public synchronized void close(){
        eventExecutors.shutdownGracefully();
    }

    public Channel getChannel(){
        return this.channel;
    }

}
