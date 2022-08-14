package cn.sq.rpc.core.transport.netty.store;

import cn.sq.rpc.core.entity.ResponseMessage;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fishawd
 * @date 2022/8/10 1:16
 */
public class ResponseStore {
    private static final Map<Long, CompletableFuture<ResponseMessage>> RESPONSE_MAP = new ConcurrentHashMap<>();

    public static void put(Long requestId, CompletableFuture<ResponseMessage> rpcResponse){
        RESPONSE_MAP.put(requestId, rpcResponse);
    }

    public static CompletableFuture<ResponseMessage> get(Long requestId){
        return RESPONSE_MAP.get(requestId);
    }

    public static boolean remove(Long requestId){
        return RESPONSE_MAP.remove(requestId) != null;
    }

    public static void complete(ResponseMessage responseMessage){
        CompletableFuture<ResponseMessage> future = RESPONSE_MAP.remove(responseMessage.getRequestId());
        if (Objects.nonNull(future)){
            future.complete(responseMessage);
        }
    }

}
