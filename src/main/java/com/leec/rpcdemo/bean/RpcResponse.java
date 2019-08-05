package com.leec.rpcdemo.bean;

import lombok.Data;

/**
 * @author lichao
 */
@Data
public class RpcResponse {
    private int status;
    private String msg;
    private Object result;
    private String requestId;
}
