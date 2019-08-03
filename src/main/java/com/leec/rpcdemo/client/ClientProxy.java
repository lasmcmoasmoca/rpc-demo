package com.leec.rpcdemo.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import com.alibaba.fastjson.JSON;
import com.leec.rpcdemo.bean.RpcRequest;
import com.leec.rpcdemo.bean.RpcResponse;

import io.netty.channel.Channel;

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
        //todo 实现不同的策略
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setArgs(args);
        request.setMethodName(method.getName());
        request.setServiceName(clazz.getName());
        request.setParameterTypes(method.getParameterTypes());
        //todo 编写自己的编解码器
        Channel channel = NettyClient.getInstance().getChannel();
        Semaphore semaphore = new Semaphore(1);
        channel.writeAndFlush(JSON.toJSONString(request)).addListener(future -> {
            if(future.isSuccess()) {
                semaphore.release();
            }
        });
        semaphore.acquire();
        //todo 如何收到结果
        return "todo";
    }
}
