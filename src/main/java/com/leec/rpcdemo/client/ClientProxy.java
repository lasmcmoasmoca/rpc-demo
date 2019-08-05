package com.leec.rpcdemo.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.leec.rpcdemo.bean.MessageFuture;
import com.leec.rpcdemo.bean.RpcRequest;
import com.leec.rpcdemo.bean.RpcResponse;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author lichao
 */
public class ClientProxy<T> implements InvocationHandler {

    private Class<T> clazz ;
    public ClientProxy(Class<T> clazz){
        this.clazz = clazz;
    }

    private final ThreadLocal<RpcResponse> threadLocal = new ThreadLocal<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setArgs(args);
        request.setMethodName(method.getName());
        request.setServiceName(clazz.getName());
        request.setParameterTypes(method.getParameterTypes());
        //todo 编写自己的编解码器
        Channel channel = NettyClient.getInstance().getChannel();
        ChannelFuture channelFuture = channel.writeAndFlush(JSON.toJSONString(request));
        MessageFuture messageFuture = new MessageFuture(request);
        NettyClientHandler.addCallBack(request.getRequestId(),messageFuture);
        Object result = messageFuture.getResult(3000,Object.class);
        return result;
    }
}
