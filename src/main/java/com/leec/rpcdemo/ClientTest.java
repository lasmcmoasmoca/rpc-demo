package com.leec.rpcdemo;

import com.leec.rpcdemo.client.Client;
import com.leec.rpcdemo.service.HelloService;

public class ClientTest {
    public static void main(String[] args) throws InterruptedException {
        HelloService helloService =  Client.getReference(HelloService.class);
        System.out.println(helloService.syaHello("test"));
    }
}
