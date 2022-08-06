package cn.sq.rpc.core.processor;

import cn.sq.rpc.core.provider.ServiceProvider;
import cn.sq.rpc.service.annotation.RpcProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author fishawd
 * @date 2022/8/7 0:37
 */
@Component
public class RpcServiceProcessor implements BeanPostProcessor {
    private Logger logger = LoggerFactory.getLogger(RpcServiceProcessor.class);
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 如果是rpc服务提供者，则注册
        if (bean.getClass().isAnnotationPresent(RpcProvider.class)){
            RpcProvider rpcProvider = bean.getClass().getAnnotation(RpcProvider.class);
            String serviceName = rpcProvider.serviceName();
            String version = rpcProvider.version();
            if ("provider".equals(serviceName)){
                serviceName = bean.getClass().getInterfaces()[0].getCanonicalName();
            }
            logger.info("发现rpc服务: beanName={}, serviceName={}, version={}}", beanName, serviceName, version);
            ServiceProvider.getInstance().addService(serviceName, bean);
        }
        return bean;
    }
}
