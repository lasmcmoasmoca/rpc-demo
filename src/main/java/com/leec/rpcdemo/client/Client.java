package com.leec.rpcdemo.client;

import java.lang.reflect.Proxy;

/**
 * @author lichao
 */
public class Client {

    public static <T> T getReference(Class<T> interfaceClass) {
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),
            new Class<?>[] {interfaceClass},
            new ClientProxy<>(interfaceClass));
    }

}
