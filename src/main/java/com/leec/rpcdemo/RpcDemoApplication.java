package com.leec.rpcdemo;

import com.leec.rpcdemo.server.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lichao
 */
@SpringBootApplication
public class RpcDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RpcDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
    }
}
