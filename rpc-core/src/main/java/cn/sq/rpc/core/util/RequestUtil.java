package cn.sq.rpc.core.util;

import cn.sq.rpc.core.serialize.JDKSerializer;
import cn.sq.rpc.core.serialize.Serializer;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fishawd
 * @date 2022/8/10 0:06
 */
public class RequestUtil {

    private static final AtomicLong ATOMIC_LONG = new AtomicLong();

    public static long getRequestId() {
        return ATOMIC_LONG.getAndIncrement();
    }

    public static Serializer getSerializer(int serialize){
        Serializer serializer = null;
        if (serialize == 1){
            serializer = new JDKSerializer();
        }
        // todo json序列化
        return serializer;
    }
}
