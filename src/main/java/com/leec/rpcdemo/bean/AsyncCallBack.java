package com.leec.rpcdemo.bean;

/**
 * @author lichao
 */
public interface AsyncCallBack<T> {
    /**
     *
     * @param millisecond
     * @param result
     * @param <T>
     * @return
     */
    <T> T getResult(int millisecond , T result);

    /**
     *
     * @param response
     */
    void call(T response);
}
