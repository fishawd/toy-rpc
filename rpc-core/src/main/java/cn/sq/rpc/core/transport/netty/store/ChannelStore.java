package cn.sq.rpc.core.transport.netty.store;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 复用channel
 * @author fishawd
 * @date 2022/8/10 0:42
 */
public class ChannelStore {
    private static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    public static Channel getChannel(String key){
        if (CHANNEL_MAP.containsKey(key)){
            Channel channel = CHANNEL_MAP.get(key);
            if (channel.isActive()){
                return channel;
            }
            CHANNEL_MAP.remove(key);
        }
        return null;
    }

    public static boolean containsChannel(String key){
        return CHANNEL_MAP.containsKey(key) && CHANNEL_MAP.get(key).isActive();
    }

    public static void put(String key, Channel channel){
        CHANNEL_MAP.put(key, channel);
    }

}
