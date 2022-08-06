package cn.sq.rpc.core.serviceImpl;

import cn.sq.rpc.service.HelloService;
import cn.sq.rpc.service.annotation.RpcProvider;
import cn.sq.rpc.service.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fishawd
 * @date 2022/7/29 23:57
 */
@RpcProvider
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String sayHello(User user) {
        String msg = "Hello, " + user.toString();
        logger.info("[HelloServiceImpl] user={}", msg);
        return msg;
    }
}
