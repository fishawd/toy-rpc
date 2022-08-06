package cn.sq.rpc.service.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author fishawd
 * @date 2022/8/6 23:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Service
public @interface RpcProvider {

    String version() default "1.0.0";

    String serviceName() default "provider";
}
