package com.leec.rpcdemo;

import com.leec.rpcdemo.register.ServiceRegister;
import com.leec.rpcdemo.server.NettyServer;
import com.leec.rpcdemo.service.HelloService;
import com.leec.rpcdemo.service.impl.HelloServiceImpl;

/**
 * @author lichao
 */
public class ServerTest {
    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
        ServiceRegister register = new ServiceRegister();
        HelloService helloService = new HelloServiceImpl();
        register.registerService(HelloService.class.getName(),helloService);
    }
}
