package com.leec.rpcdemo.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

/**
 * 后续对channel做校验，过滤无关的/丢失心跳的channel
 * @author lichao
 */
public class ChannelFilter {

    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public Channel registerChannel(String instance ,Channel channel){
        return channelMap.put(instance,channel);
    }

    public Channel getChannel(String instance){
        return channelMap.get(instance);
    }

    public Channel removeChannel(Channel channel){
        Channel result = null;
        if(channelMap.containsKey(channel)){
            result = channelMap.remove(channel);
        }
        return result;
    }
}
