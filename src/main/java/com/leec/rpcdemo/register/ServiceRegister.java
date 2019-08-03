package com.leec.rpcdemo.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.leec.rpcdemo.annotation.Register;

/**
 * @author lichao
 */
@Component
public class ServiceRegister implements ApplicationContextAware {

    private static volatile Map<String, Object> rpcServiceMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init(){
        initServiceRegister();
    }

    private void initServiceRegister(){
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(Register.class);
        for (Object rpcService : map.values()) {
            String registerName = rpcService.getClass().getAnnotation(Register.class).name().getName();
            System.out.println(String.format("register name :%s service:{}", registerName, rpcService));
            rpcServiceMap.put(registerName,rpcService);
        }
    }

    public Object registerService(String registerName, Object rpcService){
        return rpcServiceMap.put(registerName,rpcService);
    }

    public static Object getService(String registerName){
        return rpcServiceMap.get(registerName);
    }
}
