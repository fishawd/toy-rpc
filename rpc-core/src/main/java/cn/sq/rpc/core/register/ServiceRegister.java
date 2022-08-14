package cn.sq.rpc.core.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fishawd
 * @date 2022/7/31 22:24
 */
public class ServiceRegister {

    private Logger logger = LoggerFactory.getLogger(ServiceRegister.class);

    private static Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private static ServiceRegister serviceRegister = new ServiceRegister();

    private ServiceRegister(){
    }

    public static ServiceRegister getInstance(){
        return serviceRegister;
    }


    public void addService(String serviceName, Object service){
        if (!serviceMap.containsKey(serviceName)){
           serviceMap.putIfAbsent(serviceName, service);
           logger.info("服务 {} 已注册", serviceName);
        }
    }


    public Object getService(String serviceName){
        return serviceMap.get(serviceName);
    }



}
