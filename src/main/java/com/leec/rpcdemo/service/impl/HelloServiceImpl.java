package com.leec.rpcdemo.service.impl;

import com.leec.rpcdemo.annotation.Register;
import com.leec.rpcdemo.service.HelloService;
/**
 * @author lichao
 */
@Register(name = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String syaHello(String value) {
        System.out.println(value);
        return "hello" + value;
    }
}
