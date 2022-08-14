package cn.sq.rpc.core.processor;

import cn.sq.rpc.core.register.ServiceRegister;
import cn.sq.rpc.service.annotation.RpcConsumer;
import cn.sq.rpc.service.annotation.RpcProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
            logger.info("发现rpc服务提供者: beanName={}, serviceName={}, version={}}", beanName, serviceName, version);
            ServiceRegister.getInstance().addService(serviceName, bean);
        }else if (bean.getClass().isAnnotationPresent(RpcConsumer.class)){
            RpcConsumer rpcConsumer = bean.getClass().getAnnotation(RpcConsumer.class);
            String serviceName = rpcConsumer.serviceName();
            if ("consumer".equals(serviceName)){
                serviceName = bean.getClass().getInterfaces()[0].getCanonicalName();
            }
            logger.info("发现rpc服务消费者: beanName={}, serviceName={}}", beanName, serviceName);
            // todo 自动生成代理对象
            // todo 维护消费者列表
        }
        return bean;
    }
}
