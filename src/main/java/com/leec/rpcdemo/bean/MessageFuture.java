package com.leec.rpcdemo.bean;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author lichao
 */
public class MessageFuture implements AsyncCallBack<RpcResponse>{

    private RpcRequest request;

    private RpcResponse response;

    private Semaphore semaphore;

    public MessageFuture(RpcRequest rpcRequest){
        this.request = rpcRequest;
        semaphore = new Semaphore(0);
    }

    @Override
    public <T> T getResult(int millisecond, T result) {
        boolean release = false;
        try {
            release = semaphore.tryAcquire(millisecond, TimeUnit.MILLISECONDS);

        if(!release){
            System.out.println("handle time out ..requestId :"+request.getRequestId());
            return null;
        }
        } catch (InterruptedException e) {
            //ignore
            e.printStackTrace();
        }
        if(this.response ==null){
            System.out.println("error ..requestId :"+request.getRequestId());
        }
        return (T) response.getResult();
    }

    @Override
    public void call(RpcResponse response) {
        this.response = response;
        semaphore.release();
    }

}
