package cn.sq.rpc.core.listener;

import cn.sq.rpc.core.transport.Server;
import cn.sq.rpc.core.transport.netty.NettyServer;
import cn.sq.rpc.core.transport.socket.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author fishawd
 * @date 2022/8/7 1:30
 */
@Component
public class ServerInitListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(ServerInitListener.class);

    @Value("${rpcserver.host}")
    private String host;

    @Value("${rpcserver.port}")
    private Integer port;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        SocketServer socketServer = new SocketServer(host, port);
        Server server = new NettyServer(host, port);
        logger.info("RpcServer启动..., host:port={}:{}", host, port);
        server.startServer();
    }
}
