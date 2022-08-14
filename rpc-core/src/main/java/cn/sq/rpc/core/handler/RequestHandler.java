package cn.sq.rpc.core.handler;

import cn.sq.rpc.core.entity.RequestMessage;
import cn.sq.rpc.core.register.ServiceRegister;
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

    private ServiceRegister serviceRegister = ServiceRegister.getInstance();

    public Object handle(RequestMessage requestMessage){
        Object service = serviceRegister.getService(requestMessage.getInterfaceName());
        try {
            Method method = service.getClass().getMethod(requestMessage.getMethodName(), requestMessage.getParameterTypes());
            return method.invoke(service, requestMessage.getParameters());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.error("反射方法调用失败 ", e );
        }
        return null;
    }



}
