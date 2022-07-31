package cn.sq.rpc.core.transport;

import cn.sq.rpc.core.entity.RpcRequest;
import cn.sq.rpc.core.entity.RpcResponse;
import cn.sq.rpc.core.transport.socket.SocketClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author fishawd
 * @date 2022/7/31 23:58
 */
public class ClientProxy implements InvocationHandler {
    private SocketClient socketClient;

    public ClientProxy(SocketClient socketClient){
        this.socketClient = socketClient;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setInterfaceName(method.getDeclaringClass().getName());
        rpcRequest.setParameters(args);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        RpcResponse rpcResponse = socketClient.sendRequest(rpcRequest);
        if (Objects.nonNull(rpcResponse)){
            return rpcResponse.getData();
        }
        return null;
    }
}
