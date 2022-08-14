package cn.sq.rpc.core.transport;

import cn.sq.rpc.core.constant.ResponseEnum;
import cn.sq.rpc.core.entity.RequestMessage;
import cn.sq.rpc.core.entity.ResponseMessage;
import cn.sq.rpc.core.transport.netty.NettyClient;
import cn.sq.rpc.core.transport.socket.SocketClient;
import cn.sq.rpc.core.util.RequestUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author fishawd
 * @date 2022/7/31 23:58
 */
public class ClientProxy implements InvocationHandler {
    private Client client;

    public ClientProxy(Client client) {
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setRequestId(RequestUtil.getRequestId());
        System.out.println(requestMessage.getRequestId());
        requestMessage.setMethodName(method.getName());
        requestMessage.setInterfaceName(method.getDeclaringClass().getName());
        requestMessage.setParameters(args);
        requestMessage.setParameterTypes(method.getParameterTypes());
        if (client instanceof SocketClient) {
            ResponseMessage responseMessage = (ResponseMessage) client.sendRequest(requestMessage);
            if (Objects.nonNull(responseMessage) && ResponseEnum.SUCCESS.getCode().equals(responseMessage.getCode()) ) {
                return responseMessage.getData();
            }
        }else if (client instanceof NettyClient){
            CompletableFuture<ResponseMessage> resultFuture = (CompletableFuture<ResponseMessage>)client.sendRequest(requestMessage);
            return resultFuture.get().getData();
        }
        return null;
    }
}
