package cn.sq.rpc.core.transport.socket;

import cn.sq.rpc.core.handler.RequestHandler;
import cn.sq.rpc.core.transport.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fishawd
 * @date 2022/7/31 13:28
 */
public class SocketServer implements Server {

    private Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private String host;
    private int port;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 10, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(128));

    private RequestHandler requestHandler = new RequestHandler();


    public SocketServer(String host, int port){
        this.host = host;
        this.port = port;
    }
    @Override
    public void startServer(){
       try {
           ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
                   .bind(new InetSocketAddress(host, port));
           while (true){
               SocketChannel channel = serverSocketChannel.accept();
               if (Objects.nonNull(channel)){
                   logger.info("新连接 -> {}", channel.getRemoteAddress());
                   threadPoolExecutor.execute(new Worker(channel, requestHandler));
               }
           }
       }catch (IOException e){
           logger.error("连接失败 ", e);
       }
    }

}
