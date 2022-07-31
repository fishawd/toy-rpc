package cn.sq.rpc.core.handler;

import cn.sq.rpc.core.entity.RpcRequest;
import cn.sq.rpc.core.entity.RpcResponse;
import cn.sq.rpc.core.provider.ServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author fishawd
 * @date 2022/7/31 22:22
 */
public class RequestHandler {

    private Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private ServiceProvider serviceProvider = ServiceProvider.getInstance();

    public Object handle(RpcRequest rpcRequest){
        Object service = serviceProvider.getService(rpcRequest.getInterfaceName());
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            return method.invoke(service, rpcRequest.getParameters());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.error("反射方法调用失败 ", e );
        }
        return null;
    }



}
