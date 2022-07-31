package cn.sq.rpc.core.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fishawd
 * @date 2022/7/31 22:24
 */
public class ServiceProvider {

    private Logger logger = LoggerFactory.getLogger(ServiceProvider.class);

    private static Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private static ServiceProvider serviceProvider = new ServiceProvider();

    private ServiceProvider(){
    }

    public static ServiceProvider getInstance(){
        return serviceProvider;
    }


    public  void addService(String serviceName, Object service){
        if (!serviceMap.containsKey(serviceName)){
           serviceMap.putIfAbsent(serviceName, service);
           logger.info("服务 {} 已注册", serviceName);
        }
    }


    public Object getService(String serviceName){
        return serviceMap.get(serviceName);
    }



}
