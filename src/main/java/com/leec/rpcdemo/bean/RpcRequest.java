package com.leec.rpcdemo.bean;

import lombok.Data;

/**
 * @author lichao
 */
@Data
public class RpcRequest {

    private String requestId;

    private String serviceName;

    private String methodName;

    private Object[] args;

    private Class<?>[] parameterTypes;


}
